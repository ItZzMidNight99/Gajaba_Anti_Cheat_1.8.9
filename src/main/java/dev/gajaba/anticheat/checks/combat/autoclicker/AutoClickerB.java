package dev.gajaba.anticheat.checks.combat.autoclicker;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

public final class AutoClickerB extends Check {

    public AutoClickerB(GajabaLegacy plugin) {
        super(plugin, CheckType.AUTOCLICKER_B);
    }

    @Override
    public void handle(PlayerData data) {
        List<Long> clicks = data.getClickTimestamps();
        if (clicks.size() < 50) {
            return;
        }

        List<Long> delays = new ArrayList<Long>();
        for (int i = clicks.size() - 50; i < clicks.size() - 1; i++) {
            delays.add(clicks.get(i + 1) - clicks.get(i));
        }

        double mean = delays.stream().mapToLong(Long::longValue).average().orElse(0.0D);
        double variance = delays.stream().mapToDouble(d -> Math.pow(d - mean, 2)).average().orElse(0.0D);
        double stdDev = Math.sqrt(variance);

        if (stdDev < 5.0D && mean > 0.0D) {
            flag(data, String.format("Too consistent std=%.2f mean=%.2f", stdDev, mean));
        } else {
            reward(data);
        }
    }
}
