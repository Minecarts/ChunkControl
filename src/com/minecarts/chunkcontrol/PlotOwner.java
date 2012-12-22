package com.minecarts.chunkcontrol;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.OfflinePlayer;

public class PlotOwner {
    public static final PlotOwner NONE = new PlotOwner(null);

    private static final Map<OfflinePlayer, PlotOwner> owners = new WeakHashMap<OfflinePlayer, PlotOwner>();

    private OfflinePlayer player;

    private PlotOwner(OfflinePlayer player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object other) {
        if (player == null) return other == NONE;
        return player.equals(other);
    }

    public PlotOwner create(OfflinePlayer player) {
        if (player == null) return NONE;

        PlotOwner owner = owners.get(player);
        if (owner == null) {
            owner = new PlotOwner(player);
            owners.put(player, owner);
        }

        return owner;
    }

    public boolean has(OfflinePlayer actor) {
        if (actor == null) return false;
        return actor.equals(player);
    }
}
