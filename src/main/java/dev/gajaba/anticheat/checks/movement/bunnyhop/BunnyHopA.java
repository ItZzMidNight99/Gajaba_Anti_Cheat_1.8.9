package dev.gajaba.anticheat.checks.movement.bunnyhop;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class BunnyHopA extends Check {

    public BunnyHopA(GajabaLegacy plugin) {
        super(plugin, CheckType.BUNNYHOP_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.isOnGround() || data.getAirTicks() < 4) return;

        double accel = data.getDeltaXZ() - data.getLastDeltaXZ();
        if (accel > 0.045D && data.getDeltaXZ() > 0.35D) {
            flag(data, String.format("BHop accel %.4f", accel));
        } else {
            reward(data);
        }
    }
}
