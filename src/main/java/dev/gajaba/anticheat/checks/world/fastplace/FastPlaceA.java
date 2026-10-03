package dev.gajaba.anticheat.checks.world.fastplace;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class FastPlaceA extends Check {

    public FastPlaceA(GajabaLegacy plugin) {
        super(plugin, CheckType.FASTPLACE_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getLastBlockPlace() == 0L) {
            return;
        }
        long since = System.currentTimeMillis() - data.getLastBlockPlace();
        if (since < 50L) {
            flag(data, "FastPlace " + since + "ms");
        } else {
            reward(data);
        }
    }
}
