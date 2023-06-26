package me.flame.mcmapi.handler.setup;

import lombok.Getter;
import lombok.Setter;
import me.flame.mcmapi.arena.TemporaryArena;
import me.flame.mcmapi.util.menus.GuiAction;
import me.flame.mcmapi.util.menus.GuiItem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class ArenaSetupHandler implements SetupHandler {
    private final List<GuiItem> items;
    private final Map<UUID, TemporaryArena> arenas;

    public ArenaSetupHandler() {
        items = new ArrayList<>();
        arenas = new HashMap<>(500);
    }

    @Override
    public void addItem(GuiItem item) {
        var potentialItem = items.get(items.indexOf(item));
        if (potentialItem != null && Objects.equals(item.getItem().getItemMeta(), potentialItem.getItem().getItemMeta())) {
            items.add(item);
        }
    }

    @Override
    public void removeItem(GuiItem item) {
        items.remove(item);
    }

    @Override
    public void removeItem(int index) {
        items.remove(index);
    }

    @Override
    public @Nullable GuiItem getItem(Predicate<GuiItem> predicate) {
        return this.items.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public @Nullable GuiItem getItem(int index) {
        try {
            return this.items.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        items.stream()
            .filter(guiItem -> guiItem.getItem().equals(itemStack) && Objects.equals(guiItem.getItem().getItemMeta(), itemStack.getItemMeta()))
            .findFirst().map(GuiItem::getAction)
            .ifPresent(act -> act.run(event));
    }
}
