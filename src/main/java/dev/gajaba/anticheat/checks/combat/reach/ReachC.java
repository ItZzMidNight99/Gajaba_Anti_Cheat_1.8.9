package dev.gajaba.anticheat.checks.combat.reach;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class ReachC extends Check {

    public ReachC(GajabaLegacy plugin) {
        super(plugin, CheckType.REACH_C);
    }

    @Override
    public void handle(PlayerData data, Entity target) {
        Vector eye = data.getPlayer().getEyeLocation().toVector();
        double width = target.getWidth();
        double height = target.getHeight();
        Vector center = target.getLocation().toVector().add(new Vector(0, height / 2.0D, 0));

        double closestX = Math.max(center.getX() - width / 2.0D, Math.min(eye.getX(), center.getX() + width / 2.0D));
        double closestY = Math.max(center.getY() - height / 2.0D, Math.min(eye.getY(), center.getY() + height / 2.0D));
        double closestZ = Math.max(center.getZ() - width / 2.0D, Math.min(eye.getZ(), center.getZ() + width / 2.0D));

        double distance = eye.distance(new Vector(closestX, closestY, closestZ));
        if (distance > 3.1D) {
            flag(data, String.format("Box edge %.2f", distance));
        } else {
            reward(data);
        }
    }
}
