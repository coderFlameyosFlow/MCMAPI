package me.flame.mcmapi.util.menus;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        if (item.hasItemMeta())
            this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        if (item.hasItemMeta())
            this.meta.setLore(List.of(lore));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (item.hasItemMeta())
            this.meta.setLore(lore);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant) {
        if (item.hasItemMeta())
            this.meta.addEnchant(enchant, 1, false);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        if (item.hasItemMeta())
            this.meta.addEnchant(enchant, level, false);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level, boolean ignore) {
        if (item.hasItemMeta())
            this.meta.addEnchant(enchant, level, ignore);
        return this;
    }

    public ItemBuilder addEnchant(int level, boolean ignore, Enchantment... enchant) {
        if (!item.hasItemMeta()) return this;
        for (Enchantment enchantment : enchant) {
            this.meta.addEnchant(enchantment, level, ignore);
        }
        return this;
    }

    public ItemBuilder addEnchant(int level, Enchantment... enchant) {
        if (!item.hasItemMeta()) return this;
        for (Enchantment enchantment : enchant) {
            this.meta.addEnchant(enchantment, level, false);
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment... enchant) {
        if (!item.hasItemMeta()) return this;
        for (Enchantment enchantment : enchant) {
            this.meta.addEnchant(enchantment, 1, false);
        }
        return this;
    }

    public ItemBuilder setUnbreakable() {
        if (!item.hasItemMeta()) return this;
        this.meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean breakable) {
        if (!item.hasItemMeta()) return this;
        this.meta.setUnbreakable(breakable);
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        if (!item.hasItemMeta()) return this;
        if (meta instanceof Damageable) {
            var damageable = ((Damageable) meta);
            damageable.setDamage(damage);
        }
        return this;
    }

    public ItemStack build() {
        this.item.setItemMeta(meta);
        return item;
    }

    public GuiItem buildItem() {
        return new GuiItem(build(), null);
    }

    public GuiItem buildItem(@Nullable GuiAction<PlayerInteractEvent> interactionAction) {
        return new GuiItem(build(), interactionAction);
    }
}
