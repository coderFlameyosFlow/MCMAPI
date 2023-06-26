package me.flame.mcmapi;

import lombok.Getter;

import me.flame.mcmapi.arena.setup.PreferredSetup;
import me.flame.mcmapi.handler.setup.ArenaSetupHandler;
import me.flame.mcmapi.handler.setup.SetupHandler;
import me.flame.mcmapi.users.ArenaPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The main mini-game class
 * <p>
 * This is where all the magic happens and where all the managers/handlers are created and gotten.
 * <p>
 * <p>
 * Good example of initialization:
 * <pre>{@code
 *      @Getter // or normal getter
 *      private Minigame minigame;
 *
 *      // somewhere else in the code
 *      minigame = Minigame.create();
 *
 *      // other ways
 *      minigame = Minigame.create("BedWars", PreferredSetup.COMMANDS);
 *
 *      minigame = Minigame.create("BedWars"); // arena setup by default is "ITEMS", aka interacting with items
 * }</pre>
 * @since 1.0.0
 * @author flameyosflow
 */
@Getter
public final class Minigame {
    private final String name;
    private PreferredSetup preferredSetup;
    private final SetupHandler arenaSetupHandler;
    private final Plugin plugin;

    private Minigame(String name, @NotNull PreferredSetup preferredSetup, Plugin plugin) {
        this.name = name;
        this.plugin = plugin;
        this.preferredSetup = preferredSetup;
        this.arenaSetupHandler = new ArenaSetupHandler();
    }

    private Minigame(String name, @NotNull PreferredSetup preferredSetup, SetupHandler setupHandler, Plugin plugin) {
        this.name = name;
        this.plugin = plugin;
        this.preferredSetup = preferredSetup;
        this.arenaSetupHandler = setupHandler;
    }

    /**
     * Gets a new instance of <strong>Minigame</strong>.
     * <p>
     * NOTE #1: We recommend using at least {@link #create(String, Plugin)} instead for naming purposes unless you have a reason.
     * <p>
     * NOTE #2: If you would like to not setup an arena via interacting with items, check out {@link #create(String, PreferredSetup, Plugin)}
     * @return a new instance of the minigame
     * @author flameyosflow
     * @see #create(String, Plugin)
     * @see #create(PreferredSetup, Plugin)
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Minigame create(@NotNull Plugin plugin) {
        return new Minigame("Minigame", PreferredSetup.ITEMS, plugin);
    }

    /**
     * Gets a new instance of <strong>Minigame</strong>.
     * <p>
     * NOTE: If you would like to not setup an arena via interacting with items, check out {@link #create(String, PreferredSetup, Plugin)}
     * @param name the name of the minigame
     * @return a new instance of the minigame
     * @author flameyosflow
     * @see #create(String, PreferredSetup, Plugin)
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Minigame create(String name, Plugin plugin) {
        return new Minigame(name, PreferredSetup.ITEMS, plugin);
    }

    /**
     * Gets a new instance of <strong>Minigame</strong>.
     * @param name the name of the minigame
     * @param preferredSetup the preferred setup to setup arena (items/commands)
     * @return a new instance of the minigame
     * @author flameyosflow
     * @see #create(String, Plugin)
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Minigame create(String name,PreferredSetup preferredSetup, Plugin plugin) {
        return new Minigame(name, preferredSetup, plugin);
    }

    /**
     * Gets a new instance of <strong>Minigame</strong>.
     * @param name the name of the minigame
     * @param preferredSetup the preferred setup to setup arena (items/commands)
     * @param handler the custom handler, or most likely to replace the normal handler with the kt handler.
     * @param plugin the plugin used for multiple reasons.
     * @return a new instance of the minigame
     * @author flameyosflow
     * @see #create(String, Plugin)
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull Minigame create(String name, PreferredSetup preferredSetup, SetupHandler handler, @NotNull Plugin plugin) {
        return new Minigame(name, preferredSetup, handler, plugin);
    }

    /**
     * Gets a new instance of <strong>Minigame</strong>.
     * <p>
     * NOTE #1: We recommend using at least {@link #create(String, Plugin)} instead for naming purposes unless you have a reason.
     * @return a new instance of the minigame
     * @author flameyosflow
     * @see #create(String, PreferredSetup, Plugin)
     * @see #create(String, Plugin)
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Minigame create(PreferredSetup preferredSetup, Plugin plugin) {
        return new Minigame("Minigame", preferredSetup, plugin);
    }

    /**
     * Safely get a player and returns the player normally, but if the player doesn't exist, you get "null" instead of blowing up.
     * <p>
     * Although this still needs care, it's definitely less boilerplate to look at and type.
     * <p>
     * Much preferred over {@link #findArenaPlayer(String)} as it doesn't blow up if the player doesn't exist.
     * @apiNote This is always safe as long as the player exists, make sure you take good care of the nullability issues, also make sure you are not using names for persistent data containers (PDC)
     * <p>
     * if you would like to make a place where it holds players (such as a database) then we suggest looking at {@link #findArenaPlayer(UUID)}
     * @author flameyosflow
     * @see #findArenaPlayer(String)
     * @see #findArenaPlayer(UUID)
     * @see #findArenaPlayerSafely(UUID)
     * @see #findArenaPlayer(Player)
     * @param name the name of the player
     * @return the arena player object or null if the player doesn't exist
     */
    public @Nullable ArenaPlayer findArenaPlayerSafely(@NotNull String name) {
        Player player = Bukkit.getPlayer(name);
        return player != null ? new ArenaPlayer(player) : null;
    }

    /**
     * Safely get a player and returns the player normally, but if the player doesn't exist, you get "null" instead of blowing up.
     * <p>
     * Although this still needs care, it's definitely less boilerplate to look at and type.
     * <p>
     * Much preferred over {@link #findArenaPlayer(UUID)} as it doesn't blow up if the player doesn't exist.
     * @author flameyosflow
     * @apiNote This is always safe as long as the player exists, make sure you take good care of the nullability issues
     * @param uuid the uuid of the player
     * @return the arena player object or null if the player doesn't exist
     */
    public @Nullable ArenaPlayer findArenaPlayerSafely(@NotNull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null ? new ArenaPlayer(player) : null;
    }

    /**
     * Get a player and returns the player normally, but if the player doesn't exist, {@link IllegalStateException} is thrown.
     * <p>
     * It is recommended to use {@link #findArenaPlayerSafely(String)} as it doesn't blow up if the player doesn't exist.
     * @apiNote This is always safe as long as the player exists, make sure you take good care of the nullability issues, also make sure you are not using names for persistent data containers (PDC)
     * <p>
     * if you would like to make a place where it holds players (such as a database) then we suggest looking at {@link #findArenaPlayer(UUID)}
     * @author flameyosflow
     * @see #findArenaPlayerSafely(String)
     * @see #findArenaPlayerSafely(UUID)
     * @see #findArenaPlayer(Player)
     * @param name the name of the player
     * @return the arena player object
     * @throws IllegalStateException if the player doesn't exist
     */
    public @NotNull ArenaPlayer findArenaPlayer(@NotNull String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) return new ArenaPlayer(player);
        throw new IllegalStateException("Player must exist and not be null!");
    }

    /**
     * Get a player and returns the player normally, but if the player doesn't exist, {@link IllegalStateException} is thrown.
     * <p>
     * It is recommended to use {@link #findArenaPlayerSafely(String)} as it doesn't blow up if the player doesn't exist.
     * @author flameyosflow
     * @apiNote This is always safe as long as the player exists, make sure you take good care of the nullability issues
     * @param uuid the uuid of the player
     * @return the arena player object
     * @throws IllegalStateException if the player doesn't exist
     */
    public @NotNull ArenaPlayer findArenaPlayer(@NotNull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) return new ArenaPlayer(player);
        throw new IllegalStateException("Player must exist and not be null!");
    }

    /**
     * Get a player and returns the player normally, the player is guaranteed to exist.
     * <p>
     * This is the safest option as it is always non-null and does not throw a {@link IllegalStateException temper tantrum} or <a href="https://en.wikipedia.org/wiki/Null_Object_pattern">the billion dollar mistake</a>
     * @author flameyosflow
     * @param player the player object
     * @return an arena player that is never not null
     */
    public @NotNull ArenaPlayer findArenaPlayer(@NotNull Player player) {
        return new ArenaPlayer(player);
    }

    public void setPreferredSetup(PreferredSetup preferredSetup) {
        this.preferredSetup = preferredSetup;
    }
}
