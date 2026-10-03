package dev.gajaba.anticheat.checks.combat.reach;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class ReachB extends Check {

    public ReachB(GajabaLegacy plugin) {
        super(plugin, CheckType.REACH_B);
    }

    @Override
    public void handle(PlayerData data, Entity target) {
        Vector playerPos = data.getPlayer().getEyeLocation().toVector();
        Vector targetPos = target.getLocation().toVector().add(new Vector(0, target.getHeight() / 2.0D, 0));
        Vector interpolated = targetPos.clone().subtract(target.getVelocity().clone().multiply(data.getPing() / 50.0D));

        double distance = playerPos.distance(interpolated);
        if (distance > 3.15D) {
            flag(data, String.format("Reach %.2f", distance));
        } else {
            reward(data);
        }
    }
}
