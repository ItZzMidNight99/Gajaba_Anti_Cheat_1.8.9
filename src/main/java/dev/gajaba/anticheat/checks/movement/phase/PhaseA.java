package dev.gajaba.anticheat.checks.movement.phase;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.checks.Check;
import dev.gajaba.anticheat.checks.CheckType;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.block.Block;

public final class PhaseA extends Check {

    public PhaseA(GajabaLegacy plugin) {
        super(plugin, CheckType.PHASE_A);
    }

    @Override
    public void handle(PlayerData data) {
        Block at = data.getLocation().getBlock();
        Block above = data.getLocation().clone().add(0, 1, 0).getBlock();
        if ((at.getType().isSolid() || above.getType().isSolid()) && data.getDeltaY() >= 0.0D) {
            flag(data, "Inside solid block");
        } else {
            reward(data);
        }
    }
}
