package dev.gajaba.anticheat.checks.combat.autoclicker;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class AutoClickerA extends Check {

    public AutoClickerA(GajabaLegacy plugin) {
        super(plugin, CheckType.AUTOCLICKER_A);
    }

    @Override
    public void handle(PlayerData data) {
        long now = System.currentTimeMillis();
        int cps = 0;
        for (Long click : data.getClickTimestamps()) {
            if (now - click <= 1000L) {
                cps++;
            }
        }
        if (cps > 20) {
            flag(data, "CPS " + cps);
        } else {
            reward(data);
        }
    }
}
