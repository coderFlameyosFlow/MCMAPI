package me.flame.mcmapi.arena.setup;

public enum PreferredSetup {
    ITEMS;

    public String toString() {
        String name = name().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
