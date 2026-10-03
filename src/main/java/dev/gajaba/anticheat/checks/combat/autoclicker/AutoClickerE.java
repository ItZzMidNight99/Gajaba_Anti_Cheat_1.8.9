package dev.gajaba.anticheat.checks.combat.autoclicker;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

public final class AutoClickerE extends Check {

    public AutoClickerE(GajabaLegacy plugin) {
        super(plugin, CheckType.AUTOCLICKER_E);
    }

    @Override
    public void handle(PlayerData data) {
        List<Long> clicks = data.getClickTimestamps();
        if (clicks.size() < 30) return;

        List<Long> delays = new ArrayList<Long>();
        for (int i = clicks.size() - 30; i < clicks.size() - 1; i++) {
            delays.add(clicks.get(i + 1) - clicks.get(i));
        }

        int patterns = 0;
        for (int i = 2; i < delays.size(); i++) {
            if (Math.abs(delays.get(i) - delays.get(i - 1)) < 2 && Math.abs(delays.get(i - 1) - delays.get(i - 2)) < 2) {
                patterns++;
            }
        }

        double ratio = (double) patterns / delays.size();
        if (ratio > 0.7) {
            flag(data, String.format("Pattern ratio %.2f", ratio));
        } else {
            reward(data);
        }
    }
}
