package dev.gajaba.anticheat.checks.combat.aura;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class AuraF extends Check {

    public AuraF(GajabaLegacy plugin) {
        super(plugin, CheckType.AURA_F);
    }

    @Override
    public void handle(PlayerData data) {
        long sinceAttack = System.currentTimeMillis() - data.getLastAttackTime();
        if (sinceAttack < 100L && (data.getDeltaYaw() > 30.0F || data.getDeltaPitch() > 30.0F)) {
            flag(data, String.format("Post-hit snap y=%.1f p=%.1f t=%d", data.getDeltaYaw(), data.getDeltaPitch(), sinceAttack));
        } else {
            reward(data);
        }
    }
}
