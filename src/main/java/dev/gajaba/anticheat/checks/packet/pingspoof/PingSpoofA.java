package dev.gajaba.anticheat.checks.packet.pingspoof;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

import java.util.List;

public final class PingSpoofA extends Check {

    public PingSpoofA(GajabaLegacy plugin) {
        super(plugin, CheckType.PINGSPOOF_A);
    }

    @Override
    public void handle(PlayerData data) {
        int ping = data.getPing();
        if (ping < 5 || (data.getPingHistory().size() > 10 && tooConsistent(data.getPingHistory()))) {
            flag(data, "Suspicious ping " + ping);
        } else {
            reward(data);
        }
    }

    private boolean tooConsistent(List<Integer> pings) {
        double avg = pings.stream().mapToInt(Integer::intValue).average().orElse(0.0D);
        double variance = pings.stream().mapToDouble(p -> Math.pow(p - avg, 2)).average().orElse(0.0D);
        return Math.sqrt(variance) < 2.0D;
    }
}
