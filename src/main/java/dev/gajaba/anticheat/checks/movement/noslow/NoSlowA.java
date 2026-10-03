package dev.gajaba.anticheat.checks.movement.noslow;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Material;

public final class NoSlowA extends Check {

    public NoSlowA(GajabaLegacy plugin) {
        super(plugin, CheckType.NOSLOW_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().getItemInHand() == null) return;
        Material item = data.getPlayer().getItemInHand().getType();
        boolean isEating = item.isEdible() && data.getPlayer().isBlocking();
        if (!isEating) return;

        double max = 0.287D * 0.2D + 0.05D;
        if (data.getDeltaXZ() > max) {
            flag(data, String.format("Eating speed %.3f > %.3f", data.getDeltaXZ(), max));
        } else {
            reward(data);
        }
    }
}
