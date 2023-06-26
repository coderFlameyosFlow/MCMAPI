package me.flame.mcmapi.handler.setup;

import me.flame.mcmapi.util.menus.GuiItem;
import me.flame.mcmapi.util.menus.ItemBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface SetupHandler extends Listener {
    /**
     * Adds an item to the GUI, if it is EXACTLY the same as one other item then it will be ignored.
     * <p>
     * It it suggested to use the class {@link me.flame.mcmapi.util.menus.ItemBuilder} to create the GuiItem
     * with {@link ItemBuilder#buildItem()}
     * @param item
     */
    void addItem(GuiItem item);

    void removeItem(GuiItem item);

    void removeItem(int index);

    @Nullable GuiItem getItem(Predicate<GuiItem> predicate);

    @Nullable GuiItem getItem(int index);

    void onPlayerInteract(PlayerInteractEvent event);
}
