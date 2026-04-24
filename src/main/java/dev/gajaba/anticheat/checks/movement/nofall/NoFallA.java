package dev.gajaba.anticheat.checks.movement.nofall;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class NoFallA extends Check {

    public NoFallA(GajabaLegacy plugin) {
        super(plugin, CheckType.NOFALL_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.isOnGround() && !data.isLastOnGround() && data.getAirTicks() > 3) {
            reward(data);
            return;
        }
        if (!data.isOnGround() && data.getAirTicks() > 3 && data.getDeltaY() == 0.0D) {
            flag(data, "Ground spoof style motion");
        } else {
            reward(data);
        }
    }
}
