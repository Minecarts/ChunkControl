package com.minecarts.chunkcontrol;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import redis.clients.jedis.*;

public class ChunkControl extends JavaPlugin {
    protected JedisPool jedisPool;

    @Override
    public void onEnable() {
        // Copy config defaults
        getConfig().options().copyDefaults(true);

        initializeJedis();

        // Register events
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        // Register commands
        getCommand("chunkcontrol").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {
        // Clean up
        if (jedisPool != null) {
            jedisPool.destroy();
            jedisPool = null;
        }
    }


    protected void initializeJedis() {
        ConfigurationSection config = getConfig().getConfigurationSection("redis");

        jedisPool = new JedisPool(new JedisPoolConfig(),
                config.getString("host"),
                config.getInt("port"),
                config.getInt("timeout"),
                config.getString("password"));

        new JedisOperation() {
            public void run(Jedis jedis) {
                log("Connected to Redis at %s:%d/%d", jedis.getClient().getHost(), jedis.getClient().getPort(), jedis.getDB());
                log("  Ping, %s", jedis.ping());
                log("  Found %d keys", jedis.dbSize());
            }
        };
    }

    public JedisPool getJedisPool() {
        return jedisPool;
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
