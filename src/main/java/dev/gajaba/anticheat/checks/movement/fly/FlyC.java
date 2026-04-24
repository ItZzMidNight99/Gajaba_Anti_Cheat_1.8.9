package dev.gajaba.anticheat.checks.movement.fly;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class FlyC extends Check {

    public FlyC(GajabaLegacy plugin) {
        super(plugin, CheckType.FLY_C);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().getAllowFlight()) return;
        if (data.getPlayer().isFlying() && !data.getPlayer().isOp()) {
            flag(data, "Creative fly in survival");
        } else {
            reward(data);
        }
    }
}
