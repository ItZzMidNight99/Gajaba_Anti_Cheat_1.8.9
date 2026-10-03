package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class HoneypotManager {

    private final GajabaLegacy plugin;
    private final Map<UUID, UUID> decoyByTarget = new ConcurrentHashMap<UUID, UUID>();

    public HoneypotManager(GajabaLegacy plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimer(plugin, this::tick, 60L, 60L);
    }

    public boolean isDecoy(Entity entity) {
        return decoyByTarget.containsValue(entity.getUniqueId());
    }

    public void handleDecoyHit(Player attacker, Entity decoy) {
        PlayerData data = plugin.getPlayerDataManager().getData(attacker);
        data.recordRun(CheckType.HONEYPOT_A);
        data.recordFail(CheckType.HONEYPOT_A);
        data.addViolation(CheckType.HONEYPOT_A, "Hit invisible decoy entity");
        plugin.getAlertManager().handleFlag(data, CheckType.HONEYPOT_A, "Honeypot triggered");
        plugin.getPunishmentManager().handleViolation(data, CheckType.HONEYPOT_A);

        decoy.remove();
        decoyByTarget.remove(attacker.getUniqueId());
    }

    private void tick() {
        if (!plugin.getConfig().getBoolean("honeypot.enabled", true)) {
            return;
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!plugin.getTestSessionManager().isPlayerUnderTest(online.getUniqueId())) {
                clearDecoy(online.getUniqueId());
                continue;
            }
            if (decoyByTarget.containsKey(online.getUniqueId())) {
                continue;
            }

            Location base = online.getLocation().clone().add(online.getLocation().getDirection().normalize().multiply(2.3D));
            base.setY(base.getY() + 1.1D);
            ArmorStand stand = online.getWorld().spawn(base, ArmorStand.class);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setSmall(true);
            stand.setMarker(false);
            stand.setCustomNameVisible(false);
            decoyByTarget.put(online.getUniqueId(), stand.getUniqueId());

            Bukkit.getScheduler().runTaskLater(plugin, () -> clearDecoy(online.getUniqueId()), 80L);
        }
    }

    private void clearDecoy(UUID target) {
        UUID id = decoyByTarget.remove(target);
        if (id == null) return;
        for (org.bukkit.World world : Bukkit.getWorlds()) {
            Entity e = world.getEntity(id);
            if (e != null) {
                e.remove();
                return;
            }
        }
    }
}
