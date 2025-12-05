package lk.cwresport.RegionTeleport.Commands;

import lk.cwresport.RegionTeleport.RegionTeleporter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeleportToRegionCommand implements CommandExecutor {
    private RegionTeleporter plugin;

    public TeleportToRegionCommand(RegionTeleporter regionTeleporter) {
        this.plugin = regionTeleporter;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        return false;
    }
}
