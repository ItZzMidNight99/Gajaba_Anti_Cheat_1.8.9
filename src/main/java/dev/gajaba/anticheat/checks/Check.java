package dev.gajaba.anticheat.checks;

import dev.gajaba.anticheat.GajabaLegacy;
import dev.gajaba.anticheat.data.PlayerData;
import org.bukkit.entity.Entity;

public abstract class Check {

    private final GajabaLegacy plugin;
    private final CheckType checkType;

    protected Check(GajabaLegacy plugin, CheckType checkType) {
        this.plugin = plugin;
        this.checkType = checkType;
    }

    public void handle(PlayerData data) {
    }

    public void handle(PlayerData data, Entity target) {
    }

    protected void flag(PlayerData data, String detail) {
        if (data.getPlayer().hasPermission("gajaba.bypass")) {
            reward(data);
            return;
        }
        data.recordRun(checkType);
        data.recordFail(checkType);
        data.addViolation(checkType, detail);
        plugin.getAlertManager().handleFlag(data, checkType, detail);
        plugin.getPunishmentManager().handleViolation(data, checkType);
        plugin.getSetbackManager().onFlag(data, checkType);
    }

    protected void reward(PlayerData data) {
        data.recordRun(checkType);
        data.decayVl(0.02D);
        data.reduceBuffer(checkType, 0.05D);
    }

    protected GajabaLegacy getPlugin() {
        return plugin;
    }
}
