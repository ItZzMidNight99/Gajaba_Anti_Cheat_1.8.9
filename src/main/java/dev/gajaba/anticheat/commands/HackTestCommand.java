package dev.gajaba.anticheat.commands;

import dev.gajaba.anticheat.GajabaLegacy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class HackTestCommand implements CommandExecutor, TabCompleter {

    private final GajabaLegacy plugin;

    public HackTestCommand(GajabaLegacy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only command.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("gajaba.staff")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.YELLOW + "Usage: /hacktest <test|use|stop> <player>");
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not online.");
            return true;
        }

        if (action.equals("test") || action.equals("use")) {
            int seconds = plugin.getConfig().getInt("test.duration-seconds", 180);
            boolean started = plugin.getTestSessionManager().startSession(player, target, seconds);
            if (!started) {
                player.sendMessage(ChatColor.RED + "That player is already under a hack test.");
                return true;
            }
            player.sendMessage(ChatColor.GREEN + "Started test on " + target.getName() + " for " + seconds + " seconds.");
            return true;
        }

        if (action.equals("stop")) {
            plugin.getTestSessionManager().stopSession(target.getUniqueId(), "manual stop by " + player.getName());
            player.sendMessage(ChatColor.GREEN + "Stopped test on " + target.getName() + ".");
            return true;
        }

        player.sendMessage(ChatColor.YELLOW + "Usage: /hacktest <test|use|stop> <player>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filter(args[0], Arrays.asList("test", "use", "stop"));
        }
        if (args.length == 2) {
            List<String> names = new ArrayList<String>();
            for (Player online : Bukkit.getOnlinePlayers()) {
                names.add(online.getName());
            }
            return filter(args[1], names);
        }
        return Collections.emptyList();
    }

    private List<String> filter(String input, List<String> options) {
        String lower = input.toLowerCase();
        List<String> out = new ArrayList<String>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(lower)) {
                out.add(option);
            }
        }
        return out;
    }
}
