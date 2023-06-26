package me.flame.mcmapi.events;

import lombok.Getter;
import me.flame.mcmapi.arena.Arena;
import me.flame.mcmapi.states.ArenaState;
import org.bukkit.event.Cancellable;

public class ArenaStateChangeEvent extends ArenaStateAbstractEvent implements Cancellable {
    @Getter
    private final ArenaState oldArenaState, newArenaState;
    private boolean cancelled;

    public ArenaStateChangeEvent(boolean async, Arena arena, ArenaState oldState, ArenaState newState) {
        super(async, arena);
        this.oldArenaState = oldState;
        this.newArenaState = newState;
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
