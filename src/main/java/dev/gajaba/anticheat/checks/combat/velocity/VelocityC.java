package dev.gajaba.anticheat.checks.combat.velocity;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class VelocityC extends Check {

    public VelocityC(GajabaLegacy plugin) {
        super(plugin, CheckType.VELOCITY_C);
    }

    @Override
    public void handle(PlayerData data) {
        if (!data.hasPendingVelocity()) return;

        long delay = System.currentTimeMillis() - data.getVelocityTime();
        if (delay > 250L) {
            flag(data, "Velocity response delay " + delay + "ms");
            data.setPendingVelocity(false);
        } else {
            reward(data);
        }
    }
}
