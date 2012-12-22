package com.minecarts.chunkcontrol;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.*;

import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private enum SubCommand {
        INFO, BUY, SELL, ALLOW, DENY
    }

    private Plugin plugin;

    public CommandHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            label = "info";
        }
        else {
            label = args[0];
            args = (String[]) ArrayUtils.remove(args, 0);
        }
        return process(sender, label, args);
    }

    public boolean process(CommandSender sender, String label, String[] args) {
        Player player;

        switch (SubCommand.valueOf(label.toUpperCase())) {
            case INFO:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This is a player-only command.");
                    return true;
                }

                player = (Player) sender;
                player.sendMessage(Plot.at(player).toString());
                return true;

            case BUY:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This is a player-only command.");
                    return true;
                }

                player = (Player) sender;
                Plot.at(player).setOwner(player);
                player.sendMessage("Plot purchased.");
                return true;

            case SELL:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This is a player-only command.");
                    return true;
                }

                player = (Player) sender;
                Plot.at(player).setOwner();
                player.sendMessage("Plot abandoned.");
                return true;


            default:
                // TODO: send help
                return true;
        }
    }
}
