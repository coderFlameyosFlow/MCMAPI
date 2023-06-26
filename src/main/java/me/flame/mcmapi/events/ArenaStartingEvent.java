package me.flame.mcmapi.events;

import me.flame.mcmapi.arena.Arena;

import org.bukkit.event.Cancellable;

import org.jetbrains.annotations.NotNull;

public class ArenaStartingEvent extends ArenaStateAbstractEvent implements Cancellable {
    private boolean cancelled;
    public ArenaStartingEvent(boolean async, @NotNull Arena arena) {
        super(async, arena);
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
