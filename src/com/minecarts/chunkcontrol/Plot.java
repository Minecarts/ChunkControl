package com.minecarts.chunkcontrol;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Plot {
    private static Map<Chunk, Plot> plots = new WeakHashMap<Chunk, Plot>();
    private final Chunk chunk;
    private PlotOwner owner;

    private Plot(Chunk chunk, PlotOwner owner) {
        this.chunk = chunk;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object other) {
        return chunk.equals(other);
    }

    public static Plot at(Chunk chunk) {
        Plot plot = plots.get(chunk);
        if (plot == null) {
            // TODO: lookup owner, pass to constructor
            plot = new Plot(chunk, PlotOwner.NONE);
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

    public Chunk getChunk() {
        return chunk;
    }
    public PlotOwner getOwner() {
        return owner;
    }
    public Plot setOwner(PlotOwner owner) {
        if (this.owner.equals(owner)) return this;

        // TODO: store owner change
        this.owner = owner;
        return this;
    }

    public boolean allows(Player actor) {
        if (owner == PlotOwner.NONE) return true;
        if (actor == null) return false;
        if (actor.hasPermission("chunkcontrol.access")) return true;
        return owner.has(actor);
    }
}
