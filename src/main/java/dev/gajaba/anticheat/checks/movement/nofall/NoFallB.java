package dev.gajaba.anticheat.checks.movement.nofall;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class NoFallB extends Check {

    public NoFallB(GajabaLegacy plugin) {
        super(plugin, CheckType.NOFALL_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getLastDeltaY() < -0.5D && data.getDeltaY() > -0.1D && !data.isOnGround()) {
            flag(data, String.format("Fall cancel y=%.3f last=%.3f", data.getDeltaY(), data.getLastDeltaY()));
        } else {
            reward(data);
        }
    }
}
