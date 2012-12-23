package com.minecarts.chunkcontrol;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.*;

import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private enum SubCommand {
        INFO, BUY, SELL, ALLOW, DENY
    }

    private ChunkControl plugin;

    public CommandHandler(ChunkControl plugin) {
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
        Plot plot;

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
                plot = Plot.at(player);

                if (player.getTotalExperience() < plot.getValue()) {
                    player.sendMessage("You do not have enough experience to purchase this plot.");
                    return true;
                }

                int exp = player.getTotalExperience();
                player.setTotalExperience(0);
                player.setExp(0);
                player.setLevel(0);
                player.giveExp(exp - plot.getValue());
                plot.setOwner(player).addValue(plot.getValue());
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
