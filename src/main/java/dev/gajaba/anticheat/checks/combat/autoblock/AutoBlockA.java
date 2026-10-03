package dev.gajaba.anticheat.checks.combat.autoblock;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class AutoBlockA extends Check {

    public AutoBlockA(GajabaLegacy plugin) {
        super(plugin, CheckType.AUTOBLOCK_A);
    }

    @Override
    public void handle(PlayerData data) {
        long timeSinceAttack = System.currentTimeMillis() - data.getLastAttackTime();
        if (data.getPlayer().isBlocking() && timeSinceAttack < 50L) {
            flag(data, "Block+attack " + timeSinceAttack + "ms");
        } else {
            reward(data);
        }
    }
}
