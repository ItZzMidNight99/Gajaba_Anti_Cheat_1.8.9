package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public final class PunishmentManager {

    private final GajabaLegacy plugin;

    public PunishmentManager(GajabaLegacy plugin) {
        this.plugin = plugin;
    }

    public void handleViolation(PlayerData data, CheckType type) {
        double weight = plugin.getConfig().getDouble("vl.weights." + type.name(), 1.0D);
        data.addVl(weight);

        if (!plugin.getConfig().getBoolean("autopunish.enabled", true)) {
            return;
        }

        double threshold = plugin.getConfig().getDouble("autopunish.threshold-vl", 35.0D);
        if (data.getTotalVl() < threshold || data.isAutoPunished()) {
            return;
        }

        Player target = data.getPlayer();
        if (target == null || !target.isOnline()) {
            return;
        }

        data.setAutoPunished(true);
        List<String> commands = plugin.getConfig().getStringList("autopunish.commands");
        for (String raw : commands) {
            String cmd = raw.replace("%player%", target.getName())
                    .replace("%vl%", String.format("%.2f", data.getTotalVl()));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }

        plugin.getAlertManager().broadcastStaff("§8[§cAC§8] §cAutoPunish executed on §f" + target.getName() + " §7(VL: " + String.format("%.2f", data.getTotalVl()) + ")");
    }
}
