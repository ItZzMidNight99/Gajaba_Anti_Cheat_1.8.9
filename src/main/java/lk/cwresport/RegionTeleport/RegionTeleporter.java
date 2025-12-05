package lk.cwresport.RegionTeleport;

import lk.cwresport.RegionTeleport.Commands.RegionTeleporterAdminCommand;
import lk.cwresport.RegionTeleport.Commands.TeleportToRegionCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RegionTeleporter extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin Enabled.");
        getCommand("region-teleport-admin").setExecutor(new RegionTeleporterAdminCommand(this));
        getCommand("region-teleport").setExecutor(new TeleportToRegionCommand(this));


    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Disabled.");
    }
}
