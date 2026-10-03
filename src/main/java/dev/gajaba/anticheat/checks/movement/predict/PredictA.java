package dev.gajaba.anticheat.checks.movement.predict;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class PredictA extends Check {

    public PredictA(GajabaLegacy plugin) {
        super(plugin, CheckType.PREDICT_A);
    }

    @Override
    public void handle(PlayerData data) {
        double predicted = data.getLastDeltaXZ() * (data.isOnGround() ? 0.6D : 0.91D);
        double diff = Math.abs(data.getDeltaXZ() - predicted);

        if (diff > 0.22D && data.getDeltaXZ() > 0.28D) {
            flag(data, String.format("Prediction miss %.3f", diff));
        } else {
            reward(data);
        }
    }
}
