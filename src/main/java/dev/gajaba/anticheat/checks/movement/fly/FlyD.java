package dev.gajaba.anticheat.checks.movement.fly;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class FlyD extends Check {

    public FlyD(GajabaLegacy plugin) {
        super(plugin, CheckType.FLY_D);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().isFlying() || data.getPlayer().getAllowFlight() || data.isOnGround()) {
            return;
        }

        if (data.getDeltaY() > 0.0D && data.getAirTicks() > 5 && data.getTotalAscentSinceGround() > 1.35D) {
            flag(data, String.format("Ascent %.2f", data.getTotalAscentSinceGround()));
        } else {
            reward(data);
        }
    }
}
