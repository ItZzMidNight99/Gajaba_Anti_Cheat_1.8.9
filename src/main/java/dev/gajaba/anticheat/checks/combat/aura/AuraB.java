package dev.gajaba.anticheat.checks.combat.aura;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class AuraB extends Check {

    public AuraB(GajabaLegacy plugin) {
        super(plugin, CheckType.AURA_B);
    }

    @Override
    public void handle(PlayerData data, Entity target) {
        Vector eye = data.getPlayer().getEyeLocation().toVector();
        Vector targetPos = target.getLocation().toVector().add(new Vector(0, target.getHeight() / 2.0, 0));
        Vector direction = targetPos.subtract(eye);

        double requiredYaw = Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
        double requiredPitch = Math.toDegrees(Math.atan2(-direction.getY(), Math.sqrt(direction.getX() * direction.getX() + direction.getZ() * direction.getZ())));

        float actualYaw = (data.getPlayer().getLocation().getYaw() + 360F) % 360F;
        float actualPitch = data.getPlayer().getLocation().getPitch();

        double yawDiff = Math.abs(((requiredYaw + 360D) % 360D) - actualYaw);
        double pitchDiff = Math.abs(requiredPitch - actualPitch);

        if (yawDiff < 0.1 && pitchDiff < 0.1 && data.getDeltaYaw() > 5) {
            flag(data, String.format("Perfect Lock | yaw %.3f pitch %.3f", yawDiff, pitchDiff));
        } else {
            reward(data);
        }
    }
}
