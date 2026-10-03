package dev.gajaba.anticheat.managers;

import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ClientProfileManager {

    public List<String> detectLikelyClients(PlayerData data) {
        List<String> out = new ArrayList<String>();
        Map<CheckType, Integer> v = data.getViolations();

        int aura = sum(v, CheckType.AURA_A, CheckType.AURA_B, CheckType.AURA_C, CheckType.AURA_D, CheckType.AURA_E, CheckType.AURA_F);
        int ac = sum(v, CheckType.AUTOCLICKER_A, CheckType.AUTOCLICKER_B, CheckType.AUTOCLICKER_C, CheckType.AUTOCLICKER_D, CheckType.AUTOCLICKER_E);
        int move = sum(v, CheckType.FLY_B, CheckType.FLY_C, CheckType.SPEED_B, CheckType.SPEED_C, CheckType.PHASE_A, CheckType.PHASE_B);

        if (aura >= 6 && ac >= 4) out.add("Raven-like combat profile");
        if (move >= 8 && sum(v, CheckType.JESUS_A, CheckType.SPIDER_A, CheckType.STEP_A) >= 2) out.add("Wurst-like movement profile");
        if (aura >= 5 && move >= 5 && sum(v, CheckType.REACH_A, CheckType.REACH_B, CheckType.REACH_C) >= 3) out.add("LiquidBounce-like blend profile");
        if (sum(v, CheckType.VELOCITY_A, CheckType.VELOCITY_B, CheckType.VELOCITY_C) >= 4 && sum(v, CheckType.BADPACKETS_A, CheckType.BADPACKETS_B, CheckType.BADPACKETS_C) >= 3) out.add("Doomsday-like packet/velocity profile");

        return out;
    }

    private int sum(Map<CheckType, Integer> map, CheckType... types) {
        int out = 0;
        for (CheckType type : types) {
            out += map.getOrDefault(type, 0);
        }
        return out;
    }
}
