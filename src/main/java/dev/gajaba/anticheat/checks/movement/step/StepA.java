package dev.gajaba.anticheat.checks.movement.step;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class StepA extends Check {

    public StepA(GajabaLegacy plugin) {
        super(plugin, CheckType.STEP_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().isFlying() || data.getPlayer().getAllowFlight()) return;
        if (!data.isOnGround()) return;

        if (data.getDeltaY() > 0.6D && data.getDeltaY() < 1.0D && data.isLastOnGround()) {
            flag(data, String.format("Step y=%.3f", data.getDeltaY()));
        } else {
            reward(data);
        }
    }
}
