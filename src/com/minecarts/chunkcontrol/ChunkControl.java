package com.minecarts.chunkcontrol;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.Bukkit;


public class ChunkControl extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        log("%s enabled.", getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        log("%s disabled.", getDescription().getVersion());
    }


    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome!");
    }


    public void log(String message) {
        log(Level.INFO, message);
    }
    public void log(Level level, String message) {
        getLogger().log(level, message);
    }
    public void log(String message, Object... args) {
        log(String.format(message, args));
    }
    public void log(Level level, String message, Object... args) {
        log(level, String.format(message, args));
    }

    public void debug(String message) {
        log(Level.FINE, message);
    }
    public void debug(String message, Object... args) {
        debug(String.format(message, args));
    }
}
