package dev.gajaba.anticheat.checks.combat.velocity;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.util.Vector;

public final class VelocityA extends Check {

    public VelocityA(GajabaLegacy plugin) {
        super(plugin, CheckType.VELOCITY_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (!data.hasPendingVelocity()) {
            return;
        }

        Vector expected = data.getExpectedVelocity();
        Vector actual = data.getPlayer().getVelocity();

        double expectedH = Math.sqrt(expected.getX() * expected.getX() + expected.getZ() * expected.getZ());
        double actualH = Math.sqrt(actual.getX() * actual.getX() + actual.getZ() * actual.getZ());

        if (expectedH > 0.01D) {
            double reduction = (expectedH - actualH) / expectedH * 100.0D;
            if (reduction > 20.0D) {
                flag(data, String.format("Horizontal reduction %.1f%%", reduction));
            } else {
                reward(data);
            }
        }
    }
}
