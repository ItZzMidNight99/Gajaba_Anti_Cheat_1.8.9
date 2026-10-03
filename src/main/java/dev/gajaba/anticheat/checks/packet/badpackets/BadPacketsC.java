package dev.gajaba.anticheat.checks.packet.badpackets;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class BadPacketsC extends Check {

    public BadPacketsC(GajabaLegacy plugin) {
        super(plugin, CheckType.BADPACKETS_C);
    }

    @Override
    public void handle(PlayerData data) {
        float pitch = data.getPlayer().getLocation().getPitch();
        if (Math.abs(pitch) > 90.0F) {
            flag(data, String.format("Invalid pitch %.2f", pitch));
        } else {
            reward(data);
        }
    }
}
