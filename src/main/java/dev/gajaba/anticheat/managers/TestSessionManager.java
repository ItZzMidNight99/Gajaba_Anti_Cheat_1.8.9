package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import dev.gajaba.anticheat.session.TestSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class TestSessionManager {

    private final GajabaLegacy plugin;
    private final Map<UUID, TestSession> activeByTarget = new ConcurrentHashMap<UUID, TestSession>();
    private BukkitTask ticker;

    public TestSessionManager(GajabaLegacy plugin) {
        this.plugin = plugin;
        this.ticker = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 20L, 20L);
    }

    public boolean startSession(Player tester, Player target, int seconds) {
        if (activeByTarget.containsKey(target.getUniqueId())) {
            return false;
        }

        long now = System.currentTimeMillis();
        long end = now + (seconds * 1000L);
        activeByTarget.put(target.getUniqueId(), new TestSession(tester.getUniqueId(), target.getUniqueId(), now, end));
        broadcastStaff("§8[§cAC§8] §e" + tester.getName() + " started hack test on §c" + target.getName() + "§e for " + seconds + "s.");
        return true;
    }

    public boolean isPlayerUnderTest(UUID target) {
        return activeByTarget.containsKey(target);
    }

    public void stopSession(UUID targetId, String reason) {
        TestSession session = activeByTarget.remove(targetId);
        if (session == null) {
            return;
        }

        Player target = Bukkit.getPlayer(session.getTarget());
        PlayerData data = plugin.getPlayerDataManager().getData(session.getTarget());
        String name = target != null ? target.getName() : session.getTarget().toString();

        if (data == null) {
            broadcastStaff("§8[§cAC§8] §7Test ended for " + name + " (no data). Reason: " + reason);
            return;
        }

        int total = data.getViolations().values().stream().mapToInt(Integer::intValue).sum();
        String verdict = total >= plugin.getConfig().getInt("test.verdict-threshold", 10)
                ? "§cLIKELY_HACKING"
                : "§aNO_STRONG_EVIDENCE";

        List<Map.Entry<CheckType, Integer>> ranked = new ArrayList<Map.Entry<CheckType, Integer>>(data.getViolations().entrySet());
        ranked.sort(Comparator.comparingInt(Map.Entry<CheckType, Integer>::getValue).reversed());

        broadcastStaff("§8[§cAC§8] §eTest finished for §c" + name + " §8(" + reason + ")");
        broadcastStaff("§8[§cAC§8] §eClient Type: §f" + plugin.getClientTypeManager().classify(data) + " §7(brand: " + data.getClientBrand() + ")");
        broadcastStaff("§8[§cAC§8] §eVerdict: " + verdict + " §7| Violations: §f" + total + " §7| VL: §f" + String.format("%.2f", data.getTotalVl()));

        int lines = 0;
        for (Map.Entry<CheckType, Integer> entry : ranked) {
            if (lines >= 5) {
                break;
            }
            broadcastStaff("§8[§cAC§8] §7 - §f" + entry.getKey().name() + "§7: " + entry.getValue());
            lines++;
        }

        broadcastStaff("§8[§cAC§8] §eCheck outcome map (true=flagged, false=clean):");
        for (CheckType type : CheckType.values()) {
            int runCount = data.getRuns().containsKey(type) ? data.getRuns().get(type) : 0;
            int failCount = data.getFails().containsKey(type) ? data.getFails().get(type) : 0;
            boolean positive = failCount > 0;
            broadcastStaff("§8[§cAC§8] §7 * " + type.name() + " = " + (positive ? "§ctrue" : "§afalse")
                    + " §8(runs=" + runCount + ", fails=" + failCount + ")");
        }

        List<String> profiles = plugin.getClientProfileManager().detectLikelyClients(data);
        if (!profiles.isEmpty()) {
            broadcastStaff("§8[§cAC§8] §eLikely client signatures:");
            for (String profile : profiles) {
                broadcastStaff("§8[§cAC§8] §7 - §f" + profile);
            }
        }
    }

    private void tick() {
        long now = System.currentTimeMillis();
        for (TestSession session : new ArrayList<TestSession>(activeByTarget.values())) {
            if (now >= session.getEnd()) {
                stopSession(session.getTarget(), "timer elapsed");
            }
        }
    }

    public void shutdown() {
        if (ticker != null) {
            ticker.cancel();
            ticker = null;
        }
        for (UUID targetId : new ArrayList<UUID>(activeByTarget.keySet())) {
            stopSession(targetId, "plugin shutdown");
        }
    }

    private void broadcastStaff(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("gajaba.staff")) {
                online.sendMessage(message);
            }
        }
        plugin.getLogger().info(message.replace("§", ""));
    }
}
