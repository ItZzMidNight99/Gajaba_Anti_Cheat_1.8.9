package dev.gajaba.anticheat.checks.packet.badpackets;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class BadPacketsB extends Check {

    public BadPacketsB(GajabaLegacy plugin) {
        super(plugin, CheckType.BADPACKETS_B);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getDeltaXZ() > 1.2D && data.isOnGround() && data.isLastOnGround()) {
            flag(data, String.format("Ground burst %.3f", data.getDeltaXZ()));
        } else {
            reward(data);
        }
    }
}
