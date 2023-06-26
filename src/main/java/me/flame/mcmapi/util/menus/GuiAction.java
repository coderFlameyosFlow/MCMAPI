package me.flame.mcmapi.util.menus;

import org.bukkit.event.Event;

@FunctionalInterface
public interface GuiAction<E extends Event> {
    void run(E e);
}
