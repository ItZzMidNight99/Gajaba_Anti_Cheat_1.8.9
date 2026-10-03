package dev.gajaba.anticheat.checks.packet.blink;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class BlinkA extends Check {

    public BlinkA(GajabaLegacy plugin) {
        super(plugin, CheckType.BLINK_A);
    }

    @Override
    public void handle(PlayerData data) {
        long since = System.currentTimeMillis() - data.getLastFlyingPacket();
        if (since > 500L && data.getLastFlyingPacket() > 0L) {
            flag(data, "No movement packets for " + since + "ms");
        } else {
            reward(data);
        }
    }
}
