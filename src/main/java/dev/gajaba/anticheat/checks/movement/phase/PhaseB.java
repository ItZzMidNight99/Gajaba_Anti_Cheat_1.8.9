package dev.gajaba.anticheat.checks.movement.phase;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class PhaseB extends Check {

    public PhaseB(GajabaLegacy plugin) {
        super(plugin, CheckType.PHASE_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getDeltaXZ() <= 0.1D) return;

        Location from = data.getLastLocation();
        Location to = data.getLocation();
        Vector dir = to.toVector().subtract(from.toVector());
        double dist = from.distance(to);
        if (dist <= 0.0D) return;
        dir.normalize();

        for (double i = 0.0D; i < dist; i += 0.2D) {
            Location check = from.clone().add(dir.clone().multiply(i));
            if (check.getBlock().getType().isSolid()) {
                flag(data, String.format("HClip dist=%.3f", dist));
                return;
            }
        }

        reward(data);
    }
}
