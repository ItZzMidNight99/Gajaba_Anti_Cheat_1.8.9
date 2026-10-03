package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class SetbackManager {

    private final GajabaLegacy plugin;

    public SetbackManager(GajabaLegacy plugin) {
        this.plugin = plugin;
    }

    public void onFlag(PlayerData data, CheckType type) {
        if (!plugin.getConfig().getBoolean("setback.enabled", true)) {
            return;
        }

        double threshold = plugin.getConfig().getDouble("setback.threshold-vl", 15.0D);
        if (data.getTotalVl() < threshold) {
            return;
        }

        Location safe = data.getLastSafeLocation();
        Player player = data.getPlayer();
        if (safe != null && player.isOnline()) {
            player.teleport(safe);
        }

        long freezeMs = plugin.getConfig().getLong("setback.freeze-ms", 2000L);
        data.setFrozenUntil(System.currentTimeMillis() + freezeMs);
        plugin.getAlertManager().broadcastStaff("§8[§cAC§8] §eSetback applied to §f" + player.getName() + " §7for §f" + type.name());
    }
}
