package com.minecarts.chunkcontrol;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.*;

import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private enum SubCommand {
        INFO, BUY, SELL, ALLOW, DENY
    }

    private ChunkControl plugin;

    public Commands(ChunkControl plugin) {
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

        switch (SubCommand.valueOf(label.toUpperCase())) {
            case INFO:
                return info(sender, args);
            case BUY:
                return buy(sender, args);
            case SELL:
                return sell(sender, args);
            case ALLOW:
                return allow(sender, args);
            case DENY:
                return deny(sender, args);
            default:
                return help(sender, args);
        }
    }


    private boolean info(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player-only command.");
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage(Plot.at(player).toString());
        return true;
    }

    private boolean buy(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player-only command.");
            return true;
        }

        Player player = (Player) sender;
        Plot plot = Plot.at(player);

        if (player.getTotalExperience() < plot.getValue()) {
            player.sendMessage("You do not have enough experience to purchase this plot.");
            return true;
        }

        int exp = player.getTotalExperience();
        player.setTotalExperience(0);
        player.setExp(0);
        player.setLevel(0);
        player.giveExp(exp - (int) plot.getValue());
        plot.setOwner(player);
        plot.addValue(plot.getValue());
        player.sendMessage("Plot purchased.");
        return true;
    }

    private boolean sell(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player-only command.");
            return true;
        }

        Player player = (Player) sender;
        Plot.at(player).setOwner();
        player.sendMessage("Plot abandoned.");
        return true;
    }

    private boolean allow(CommandSender sender, String[] args) {
        return false;
    }
    private boolean deny(CommandSender sender, String[] args) {
        return false;
    }
    private boolean help(CommandSender sender, String[] args) {
        return false;
    }
}
