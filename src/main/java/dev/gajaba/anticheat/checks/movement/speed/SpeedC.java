package dev.gajaba.anticheat.checks.movement.speed;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Material;

public final class SpeedC extends Check {

    public SpeedC(GajabaLegacy plugin) {
        super(plugin, CheckType.SPEED_C);
    }

    @Override
    public void handle(PlayerData data) {
        Material below = data.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
        boolean onIce = below == Material.ICE || below == Material.PACKED_ICE;
        if (!onIce || data.getPlayer().isFlying()) return;

        double max = 0.287D * 1.4D + 0.08D;
        if (data.getPlayer().isSprinting()) {
            max *= 1.3D;
        }

        if (data.getDeltaXZ() > max) {
            flag(data, String.format("Ice speed %.3f > %.3f", data.getDeltaXZ(), max));
        } else {
            reward(data);
        }
    }
}
