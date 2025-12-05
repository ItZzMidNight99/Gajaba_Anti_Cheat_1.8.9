package lk.cwresport.RegionTeleport.Commands;

import lk.cwresport.RegionTeleport.RegionTeleporter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class RegionTeleporterAdminCommand implements CommandExecutor {
    private RegionTeleporter plugin;
    public RegionTeleporterAdminCommand(RegionTeleporter regionTeleporter) {

        this.plugin = regionTeleporter;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length ==0){

            commandSender.sendMessage("Usage: /region-teleport-admin create");
        }
        if(args[0].equalsIgnoreCase("create")){

            if (commandSender.isOp() || commandSender.hasPermission("cwr-core.region-teleporter.admin")){
                String name = args[1];



            }






        }



        return false;
    }
}
