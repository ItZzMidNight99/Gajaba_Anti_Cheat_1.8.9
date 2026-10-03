package dev.gajaba.anticheat.checks.combat.criticals;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class CriticalsA extends Check {

    public CriticalsA(GajabaLegacy plugin) {
        super(plugin, CheckType.CRITICALS_A);
    }

    @Override
    public void handle(PlayerData data) {
        long sinceAttack = System.currentTimeMillis() - data.getLastAttackTime();
        if (sinceAttack > 500L) {
            return;
        }

        if (data.getDeltaY() > 0.0D && data.getDeltaY() < 0.2D && !data.isOnGround() && data.getAirTicks() < 3) {
            flag(data, String.format("Mini jump y=%.4f", data.getDeltaY()));
        } else {
            reward(data);
        }
    }
}
