package me.flame.mcmapi.states;

import me.flame.mcmapi.arena.Arena;
import me.flame.mcmapi.events.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public enum ArenaState {
    WAITING,
    STARTING,
    ACTIVE,
    FINISHING;

    public boolean isFinished() {
        return this == FINISHING;
    }

    public boolean isStarting() {
        return this == STARTING;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isWaiting() {
        return this == WAITING;
    }

    public void callRelevantEvent(Arena arena, Plugin plugin) {
        var pluginManager = plugin.getServer().getPluginManager();
        switch (this) {
            case WAITING:
                pluginManager.callEvent(new ArenaWaitingEvent(!Bukkit.isPrimaryThread(), arena));
                break;
            case STARTING:
                pluginManager.callEvent(new ArenaStartingEvent(!Bukkit.isPrimaryThread(), arena));
                break;
            case ACTIVE:
                pluginManager.callEvent(new ArenaActiveEvent(!Bukkit.isPrimaryThread(), arena));
                break;
            case FINISHING:
                pluginManager.callEvent(new ArenaFinishingEvent(!Bukkit.isPrimaryThread(), arena));
                break;
        }
    }
}
