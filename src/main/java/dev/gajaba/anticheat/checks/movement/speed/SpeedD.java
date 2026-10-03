package dev.gajaba.anticheat.checks.movement.speed;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Material;

public final class SpeedD extends Check {

    public SpeedD(GajabaLegacy plugin) {
        super(plugin, CheckType.SPEED_D);
    }

    @Override
    public void handle(PlayerData data) {
        Material below = data.getLocation().clone().subtract(0, 1, 0).getBlock().getType();
        if (below != Material.SLIME_BLOCK) return;

        if (data.getDeltaY() > 0.42D && data.getAirTicks() > 10) {
            flag(data, String.format("Slime exploit y=%.3f air=%d", data.getDeltaY(), data.getAirTicks()));
        } else {
            reward(data);
        }
    }
}
