package me.flame.mcmapi.events;

import me.flame.mcmapi.arena.Arena;

import org.jetbrains.annotations.NotNull;

public class CancelStartEvent extends ArenaStateAbstractEvent {
    public CancelStartEvent(boolean async, @NotNull Arena arena) {
        super(async, arena);
    }
}
