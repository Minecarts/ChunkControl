package com.minecarts.chunkcontrol;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.block.*;
import org.bukkit.event.hanging.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EventListener implements Listener {
    private Plugin plugin;

    public EventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void checkBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if(!Plot.at(event.getBlock()).allows(player)) {
            event.setCancelled(true);
            player.sendMessage("You do not have permission to place that here.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void checkBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(!Plot.at(event.getBlock()).allows(player)) {
            event.setCancelled(true);
            player.sendMessage("You do not have permission to break this.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void checkHangingPlace(HangingPlaceEvent event) {
        Player player = event.getPlayer();

        if(!Plot.at(event.getBlock()).allows(player)) {
            event.setCancelled(true);
            player.sendMessage("You do not have permission to place that here.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void checkHangingBreak(HangingBreakEvent event) {
        if (!(event instanceof HangingBreakByEntityEvent)) return;
        Entity remover = ((HangingBreakByEntityEvent) event).getRemover();

        if (!(remover instanceof Player)) return;
        Player player = (Player) remover;

        if(!Plot.at(event.getEntity().getLocation()).allows(player)) {
            event.setCancelled(true);
            player.sendMessage("You do not have permission to remove this.");
        }
    }
}
