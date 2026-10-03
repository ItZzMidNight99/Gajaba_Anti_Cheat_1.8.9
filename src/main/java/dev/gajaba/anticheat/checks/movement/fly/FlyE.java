package dev.gajaba.anticheat.checks.movement.fly;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class FlyE extends Check {

    public FlyE(GajabaLegacy plugin) {
        super(plugin, CheckType.FLY_E);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().isFlying() || data.getPlayer().getAllowFlight()) return;

        double dist = data.getLocation().distance(data.getLastLocation());
        if (dist > 10.0D && !data.isOnGround() && data.getAirTicks() > 3) {
            flag(data, String.format("Instant move %.2f", dist));
        } else {
            reward(data);
        }
    }
}
