package dev.gajaba.anticheat.checks.combat.aura;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;

public final class AuraA extends Check {

    public AuraA(GajabaLegacy plugin) {
        super(plugin, CheckType.AURA_A);
    }

    @Override
    public void handle(PlayerData data, Entity target) {
        if (data.getDeltaYaw() > 90 || data.getDeltaPitch() > 90) {
            flag(data, String.format("Snap | Yaw %.1f Pitch %.1f", data.getDeltaYaw(), data.getDeltaPitch()));
        } else {
            reward(data);
        }
    }
}
