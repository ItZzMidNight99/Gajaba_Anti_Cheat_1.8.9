package dev.gajaba.anticheat.checks.packet.badpackets;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class BadPacketsA extends Check {

    public BadPacketsA(GajabaLegacy plugin) {
        super(plugin, CheckType.BADPACKETS_A);
    }

    @Override
    public void handle(PlayerData data) {
        long sinceAttack = System.currentTimeMillis() - data.getLastAttackTime();
        if (sinceAttack < 120L && data.getDeltaYaw() == 0.0F && data.getDeltaPitch() == 0.0F && data.getClickTimestamps().size() > 20) {
            flag(data, "Static rotation spam attacks");
        } else {
            reward(data);
        }
    }
}
