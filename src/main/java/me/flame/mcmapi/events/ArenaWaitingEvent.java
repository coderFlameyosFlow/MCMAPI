package me.flame.mcmapi.events;

import me.flame.mcmapi.arena.Arena;

public class ArenaWaitingEvent extends ArenaStateAbstractEvent {
    public ArenaWaitingEvent(boolean async, Arena arena) {
        super(async, arena);
    }
}
