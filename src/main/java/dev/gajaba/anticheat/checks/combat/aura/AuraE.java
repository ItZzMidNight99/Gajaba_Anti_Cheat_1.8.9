package dev.gajaba.anticheat.checks.combat.aura;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class AuraE extends Check {

    public AuraE(GajabaLegacy plugin) {
        super(plugin, CheckType.AURA_E);
    }

    @Override
    public void handle(PlayerData data, Entity target) {
        Vector direction = data.getPlayer().getLocation().getDirection().normalize();
        Vector toTarget = target.getLocation().toVector().subtract(data.getPlayer().getEyeLocation().toVector()).normalize();
        double angle = Math.toDegrees(Math.acos(Math.max(-1.0D, Math.min(1.0D, direction.dot(toTarget)))));

        if (angle > 90.0D) {
            flag(data, String.format("Out of FOV %.1f", angle));
        } else {
            reward(data);
        }
    }
}
