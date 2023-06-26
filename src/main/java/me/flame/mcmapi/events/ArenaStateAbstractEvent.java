package me.flame.mcmapi.events;

import me.flame.mcmapi.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArenaStateAbstractEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    protected final Arena arena;

    public ArenaStateAbstractEvent(boolean async, Arena arena) {
        super(async);
        this.arena = arena;
    }

    public @NotNull Arena getArena() {
        return arena;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
