package me.flame.mcmapi.events;

import me.flame.mcmapi.arena.Arena;

public class ArenaFinishingEvent extends ArenaStateAbstractEvent {
    public ArenaFinishingEvent(boolean async, Arena arena) {
        super(async, arena);
    }
}
