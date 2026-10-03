package dev.gajaba.anticheat.checks.combat.reach;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;

public final class ReachA extends Check {

    public ReachA(GajabaLegacy plugin) {
        super(plugin, CheckType.REACH_A);
    }

    @Override
    public void handle(PlayerData data, Entity target) {
        double eyeToFeet = data.getPlayer().getEyeLocation().distance(target.getLocation());
        if (eyeToFeet > 3.4D + (data.getPing() / 1000.0D)) {
            flag(data, String.format("Raw reach %.2f", eyeToFeet));
        } else {
            reward(data);
        }
    }
}
