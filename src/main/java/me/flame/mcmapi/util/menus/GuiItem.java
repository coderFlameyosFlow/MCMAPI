package me.flame.mcmapi.util.menus;

import lombok.NonNull;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * A Gui item which was particularly made to have an action on interaction.
 * <p>
 * Basically triumph-gui gui items if instead of a click event they had an interaction event by a player.
 * <p></p>
 * You should also note this was primarily made for {@link me.flame.mcmapi.handler.setup.SetupHandler SetupHandler} and it's implementations.
 * <p>
 * Good example of using "GuiItem":
 * <pre>{@code
 *      var item = ...;
 *      var setupHandler = minigame.getArenaSetupHandler();
 *
 *      // getting an existing item
 *      var guiItem = setupHandler.getItem(guiItem -> guiItem.getItem().equals(item)
 *            && Objects.equals(item.getItemMeta(), guiItem.getItem().getItemMeta());
 *      // or if you are sure of the ordering
 *      var guiItem = setupHandler.getItem(0); // much faster alternative
 *      guiItem.setAction(event -> {
 *          ...
 *      });
 *
 *      // implementing a new item:
 *      setupHandler.addItem(new ItemBuilder(item, 2) // 2 is the amount of items you get from this "ItemBuilder"
 *                          .setName(...)
 *                          .setLore(...)
 *                          .buildItem(() -> ...);
 *      // the lambda is optional and you do not have to provide an action, you can use ItemBuilder#buildItem()
 * }</pre>
 */
public class GuiItem {
    private @Nullable GuiAction<PlayerInteractEvent> action;
    private final ItemStack item;
    private final UUID uuid = UUID.randomUUID();

    GuiItem(@NonNull ItemStack item, @Nullable GuiAction<PlayerInteractEvent> action) {
        this.item = item;
        this.action = action;
    }

    public @Nullable GuiAction<PlayerInteractEvent> getAction() {
        return action;
    }

    public void setAction(@Nullable GuiAction<PlayerInteractEvent> action) {
        this.action = action;
    }

    public ItemStack getItem() {
        return item;
    }

    public UUID getUuid() {
        return uuid;
    }
}
