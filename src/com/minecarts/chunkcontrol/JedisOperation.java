package com.minecarts.chunkcontrol;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class JedisOperation implements Runnable {
    private JedisPool jedisPool;

    public JedisOperation(JedisPool jedisPool) {
        this(jedisPool, true);
    }
    public JedisOperation(JedisPool jedisPool, boolean run) {
        this.jedisPool = jedisPool;
        if (run) run();
    }

    public JedisOperation() {
        this(true);
    }
    public JedisOperation(boolean run) {
        for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin instanceof ChunkControl) {
                this.jedisPool = ((ChunkControl) plugin).getJedisPool();
                if (run) run();
                break;
            }
        }
    }

    abstract void run(Jedis jedis);

    public void run() {
        Jedis jedis = jedisPool.getResource();
        try {
            run(jedis);
        }
        catch(JedisException e) {
            jedisPool.returnBrokenResource(jedis);
        }
        finally {
            jedisPool.returnResource(jedis);
        }
    }
}
