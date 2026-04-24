package dev.gajaba.anticheat.checks.movement.fly;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class FlyB extends Check {

    public FlyB(GajabaLegacy plugin) {
        super(plugin, CheckType.FLY_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().isFlying() || data.getPlayer().getAllowFlight()) return;
        if (data.isOnGround() || data.getAirTicks() < 10) return;

        if (Math.abs(data.getDeltaY()) < 0.05D && data.getAirTicks() > 20) {
            flag(data, String.format("Glide Y=%.4f air=%d", data.getDeltaY(), data.getAirTicks()));
        } else {
            reward(data);
        }
    }
}
