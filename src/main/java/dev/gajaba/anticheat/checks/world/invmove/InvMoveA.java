package dev.gajaba.anticheat.checks.world.invmove;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;

public final class InvMoveA extends Check {

    public InvMoveA(GajabaLegacy plugin) {
        super(plugin, CheckType.INVMOVE_A);
    }

    @Override
    public void handle(PlayerData data) {
        if (data.getPlayer().getOpenInventory() == null) {
            return;
        }
        if (data.getDeltaXZ() > 0.1D) {
            flag(data, String.format("InvMove %.3f", data.getDeltaXZ()));
        } else {
            reward(data);
        }
    }
}
