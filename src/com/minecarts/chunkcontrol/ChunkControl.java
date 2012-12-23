package com.minecarts.chunkcontrol;

import java.util.logging.Level;
import java.util.concurrent.TimeUnit;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;

public class ChunkControl extends JavaPlugin {
    protected static ChunkControl plugin;
    protected RedisClient redisClient;
    protected RedisConnection<String, String> redis;

    @Override
    public void onEnable() {
        plugin = this;

        // Copy config defaults
        getConfig().options().copyDefaults(true);

        initializeRedis();

        // Register events
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        // Register commands
        getCommand("chunkcontrol").setExecutor(new Commands(this));
    }

    @Override
    public void onDisable() {
        // Clean up
        if (redisClient != null) {
            redisClient.shutdown();
            redisClient = null;
        }
    }


    public static ChunkControl getPlugin() {
        return plugin;
    }


    protected void initializeRedis() {
        ConfigurationSection config = getConfig().getConfigurationSection("redis");
        String host = config.getString("host", "localhost");
        int port = config.getInt("port", 6379);
        int timeout = config.getInt("timeout", 5000);

        redisClient = new RedisClient(host, port);
        redisClient.setDefaultTimeout(timeout, TimeUnit.MILLISECONDS);

        log("Connecting to Redis at %s:%d", host, port);
        log("  Ping? %s", redis().ping());
        log("  Found %d keys", redis().dbsize());
    }

    public RedisConnection<String, String> redis() {
        if (redis == null) redis = connect();
        return redis;
    }

    public RedisConnection<String, String> connect() {
        RedisConnection<String, String> connection = redisClient.connect();

        String password = getConfig().getString("redis.password");
        if (password != null) connection.auth(password);

        return connection;
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
