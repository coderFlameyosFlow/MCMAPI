package me.flame.mcmapi.events;

import me.flame.mcmapi.arena.Arena;

public class ArenaActiveEvent extends ArenaStateAbstractEvent {
    public ArenaActiveEvent(boolean async, Arena arena) {
        super(async, arena);
    }
}
