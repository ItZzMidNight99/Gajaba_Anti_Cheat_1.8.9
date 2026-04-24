package dev.gajaba.anticheat.checks.movement.speed;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class SpeedB extends Check {

    public SpeedB(GajabaLegacy plugin) {
        super(plugin, CheckType.SPEED_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.isOnGround() || data.getPlayer().isFlying() || data.getPlayer().getAllowFlight()) {
            return;
        }
        if (data.getAirTicks() < 5) {
            return;
        }

        double acceleration = data.getDeltaXZ() - data.getLastDeltaXZ();
        double maxAcceleration = 0.026D + (data.getPing() / 1000.0D * 0.02D);

        if (acceleration > maxAcceleration && data.getDeltaXZ() > 0.1D) {
            flag(data, String.format("Air accel %.4f max %.4f", acceleration, maxAcceleration));
        } else {
            reward(data);
        }
    }
}
