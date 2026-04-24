package dev.gajaba.anticheat.checks.movement.spider;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class SpiderA extends Check {

    public SpiderA(GajabaLegacy plugin) {
        super(plugin, CheckType.SPIDER_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().isFlying() || data.getPlayer().getAllowFlight() || data.isOnGround()) return;
        if (data.getDeltaY() <= 0.1D) return;

        Block at = data.getLocation().getBlock();
        if (at.getType() == Material.LADDER || at.getType() == Material.VINE) {
            reward(data);
            return;
        }

        Block north = data.getLocation().clone().add(0, 0, -1).getBlock();
        Block south = data.getLocation().clone().add(0, 0, 1).getBlock();
        Block east = data.getLocation().clone().add(1, 0, 0).getBlock();
        Block west = data.getLocation().clone().add(-1, 0, 0).getBlock();

        if (north.getType().isSolid() || south.getType().isSolid() || east.getType().isSolid() || west.getType().isSolid()) {
            flag(data, String.format("Wall climb y=%.3f", data.getDeltaY()));
        } else {
            reward(data);
        }
    }
}
