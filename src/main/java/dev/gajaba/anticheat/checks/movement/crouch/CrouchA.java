package dev.gajaba.anticheat.checks.movement.crouch;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class CrouchA extends Check {

    public CrouchA(GajabaLegacy plugin) {
        super(plugin, CheckType.CROUCH_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (!data.getPlayer().isSneaking()) return;

        double max = 0.15D;
        if (data.getDeltaXZ() > max) {
            flag(data, String.format("Sneak speed %.3f > %.3f", data.getDeltaXZ(), max));
        } else {
            reward(data);
        }
    }
}
