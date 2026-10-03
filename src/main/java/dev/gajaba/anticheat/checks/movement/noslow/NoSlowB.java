package dev.gajaba.anticheat.checks.movement.noslow;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Material;

public final class NoSlowB extends Check {

    public NoSlowB(GajabaLegacy plugin) {
        super(plugin, CheckType.NOSLOW_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (!data.getPlayer().isBlocking()) return;

        Material type = data.getPlayer().getItemInHand() == null ? Material.AIR : data.getPlayer().getItemInHand().getType();
        if (!type.name().contains("SWORD")) return;

        if (data.getPlayer().isSprinting()) {
            flag(data, "Sprinting while sword-blocking");
            return;
        }

        double max = 0.287D * 0.2D + 0.05D;
        if (data.getDeltaXZ() > max) {
            flag(data, String.format("Blocking speed %.3f > %.3f", data.getDeltaXZ(), max));
        } else {
            reward(data);
        }
    }
}
