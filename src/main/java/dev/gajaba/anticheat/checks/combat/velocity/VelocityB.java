package dev.gajaba.anticheat.checks.combat.velocity;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class VelocityB extends Check {

    public VelocityB(GajabaLegacy plugin) {
        super(plugin, CheckType.VELOCITY_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (!data.hasPendingVelocity()) {
            return;
        }
        double expectedY = data.getExpectedVelocity().getY();
        if (Math.abs(expectedY) < 0.01D) {
            return;
        }
        double actualY = data.getPlayer().getVelocity().getY();
        double reduction = (expectedY - actualY) / expectedY * 100.0D;

        if (reduction > 20.0D) {
            flag(data, String.format("Vertical reduction %.1f%%", reduction));
        } else {
            reward(data);
        }

        data.setPendingVelocity(false);
    }
}
