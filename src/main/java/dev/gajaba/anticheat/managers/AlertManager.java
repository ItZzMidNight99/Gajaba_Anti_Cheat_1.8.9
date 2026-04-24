package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public final class AlertManager {

    private final GajabaLegacy plugin;

    public AlertManager(GajabaLegacy plugin) {
        this.plugin = plugin;
    }

    public void handleFlag(PlayerData data, CheckType type, String detail) {
        if (!plugin.getTestSessionManager().isPlayerUnderTest(data.getPlayer().getUniqueId())) {
            return;
        }

        String msg = "§8[§cAC§8] §7" + data.getPlayer().getName() + " §f" + type.name() + " §8» §7" + detail
                + " §8(VL: " + String.format("%.2f", data.getTotalVl()) + ")";

        broadcastStaff(msg);
        sendBungeeAlert(data.getPlayer().getName(), type.name(), detail, data.getTotalVl());
    }

    public void broadcastStaff(String msg) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("gajaba.staff")) {
                    online.sendMessage(msg);
                }
            }
        });
    }

    private void sendBungeeAlert(String player, String check, String detail, double vl) {
        if (!plugin.getConfig().getBoolean("alerts.bungee.enabled", true)) {
            return;
        }

        Player carrier = Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);
        if (carrier == null) {
            return;
        }

        plugin.getThreadManager().submit(() -> {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(baos);
                out.writeUTF("Message");
                out.writeUTF(plugin.getConfig().getString("alerts.bungee.channel", "staff"));
                out.writeUTF("[GajabaLegacy] " + player + " failed " + check + " (VL " + String.format("%.2f", vl) + ") " + detail);

                Bukkit.getScheduler().runTask(plugin, () -> carrier.sendPluginMessage(plugin, "BungeeCord", baos.toByteArray()));
            } catch (Exception ignored) {
            }
        });
    }
}
