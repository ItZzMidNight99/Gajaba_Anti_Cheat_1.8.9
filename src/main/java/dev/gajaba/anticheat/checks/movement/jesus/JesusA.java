package dev.gajaba.anticheat.checks.movement.jesus;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Material;

public final class JesusA extends Check {

    public JesusA(GajabaLegacy plugin) {
        super(plugin, CheckType.JESUS_A);
    }

    @Override
    public void handle(PlayerData data) {
        Material type = data.getLocation().getBlock().getType();
        boolean water = type == Material.WATER || type == Material.STATIONARY_WATER;
        if (!water || data.getPlayer().isFlying() || data.getPlayer().getAllowFlight()) return;

        if (Math.abs(data.getDeltaY()) < 0.01D && !data.getPlayer().isSneaking()) {
            flag(data, String.format("Water walk y=%.4f", data.getDeltaY()));
        } else {
            reward(data);
        }
    }
}
