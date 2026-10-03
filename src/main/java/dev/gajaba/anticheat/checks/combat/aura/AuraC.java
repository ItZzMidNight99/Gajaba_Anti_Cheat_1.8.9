package dev.gajaba.anticheat.checks.combat.aura;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;

import java.util.List;

public final class AuraC extends Check {

    public AuraC(GajabaLegacy plugin) {
        super(plugin, CheckType.AURA_C);
    }

    @Override
    public void handle(PlayerData data) {
        List<PlayerData.AttackRecord> history = data.getAttackHistory();
        if (history.size() < 3) {
            return;
        }

        long now = System.currentTimeMillis();
        long timeDiff = now - history.get(history.size() - 3).getTime();
        if (timeDiff >= 500) {
            reward(data);
            return;
        }

        Entity e1 = history.get(history.size() - 1).getEntity();
        Entity e2 = history.get(history.size() - 2).getEntity();
        Entity e3 = history.get(history.size() - 3).getEntity();

        if (!e1.equals(e2) && !e2.equals(e3) && !e1.equals(e3)) {
            flag(data, "Multi-target in " + timeDiff + "ms");
        } else {
            reward(data);
        }
    }
}
