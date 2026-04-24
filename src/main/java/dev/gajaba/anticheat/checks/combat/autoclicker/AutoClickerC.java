package dev.gajaba.anticheat.checks.combat.autoclicker;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

import java.util.ArrayList;
import java.util.List;

public final class AutoClickerC extends Check {

    public AutoClickerC(GajabaLegacy plugin) {
        super(plugin, CheckType.AUTOCLICKER_C);
    }

    @Override
    public void handle(PlayerData data) {
        List<Long> clicks = data.getClickTimestamps();
        if (clicks.size() < 50) return;

        List<Long> delays = new ArrayList<Long>();
        for (int i = clicks.size() - 50; i < clicks.size() - 1; i++) {
            delays.add(clicks.get(i + 1) - clicks.get(i));
        }

        double mean = delays.stream().mapToLong(Long::longValue).average().orElse(0.0);
        double std = Math.sqrt(delays.stream().mapToDouble(d -> Math.pow(d - mean, 2)).average().orElse(0.0));
        double skew = delays.stream().mapToDouble(d -> std == 0 ? 0 : Math.pow((d - mean) / std, 3)).average().orElse(0.0);

        if (Math.abs(skew) < 0.1 && std > 5.0) {
            flag(data, String.format("Skew %.3f std %.2f", skew, std));
        } else {
            reward(data);
        }
    }
}
