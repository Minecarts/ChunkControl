package com.minecarts.chunkcontrol;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;

public class Plot {
    private static Map<Chunk, Plot> plots = new WeakHashMap<Chunk, Plot>();
    private final Chunk chunk;
    private PlotOwner owner;
    private int value;

    private Plot(Chunk chunk, PlotOwner owner, int value) {
        this.chunk = chunk;
        this.owner = owner;
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return chunk.equals(other);
    }

    @Override
    public String toString() {
        return String.format("Plot [%d, %d] owned by %s has a value of %d", chunk.getX(), chunk.getZ(), (owner == PlotOwner.NONE ? "no one" : owner), value);
    }

    public static Plot at(Chunk chunk) {
        Plot plot = plots.get(chunk);
        if (plot == null) {
            // TODO: lookup owner and value, pass to constructor
            plot = new Plot(chunk, PlotOwner.NONE, 10);
            plots.put(chunk, plot);
        }
        return plot;
    }
    public static Plot at(Block block) {
        return at(block.getChunk());
    }
    public static Plot at(Location location) {
        return at(location.getChunk());
    }
    public static Plot at(Entity entity) {
        return at(entity.getLocation());
    }

    public Chunk getChunk() {
        return chunk;
    }
    public PlotOwner getOwner() {
        return owner;
    }
    public Plot setOwner() {
        return setOwner(PlotOwner.NONE);
    }
    public Plot setOwner(Player player) {
        return setOwner(PlotOwner.create(player));
    }
    public Plot setOwner(PlotOwner owner) {
        if (this.owner.equals(owner)) return this;

        // TODO: store owner change
        this.owner = owner;
        return this;
    }
    public int getValue() {
        return value;
    }
    public Plot setValue(int value) {
        this.value = value;
        return this;
    }
    public Plot addValue(int value) {
        this.value += value;
        return this;
    }

    public boolean allows(Player actor) {
        if (owner == PlotOwner.NONE) return true;
        if (actor == null) return false;
        if (actor.hasPermission("chunkcontrol.access")) return true;
        return owner.has(actor);
    }
}
