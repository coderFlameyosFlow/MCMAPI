package me.flame.mcmapi.arena;

import lombok.*;

import me.flame.mcmapi.events.ArenaStateChangeEvent;
import me.flame.mcmapi.states.ArenaState;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class Arena {
    private final String displayName;
    private final List<Set<Location>> teamLocations;
    private final int minimumTeamSize;
    private final int maximumTeamSize;
    private ArenaState state;

    public Arena(String displayName, List<Set<Location>> teamLocations, int maxTeamSize, int minTeamSize) {
        this.displayName = displayName;
        this.teamLocations = teamLocations;
        this.minimumTeamSize = minTeamSize;
        this.maximumTeamSize = maxTeamSize;
        this.state = ArenaState.WAITING;
    }

    public Arena(String displayName, List<Set<Location>> teamLocations, ArenaState state, int maxTeamSize, int minTeamSize) {
        this.displayName = displayName;
        this.teamLocations = teamLocations;
        this.minimumTeamSize = minTeamSize;
        this.maximumTeamSize = maxTeamSize;
        this.state = state;
    }

    public String getConfigName() {
        return displayName.replace(' ', '_').toUpperCase();
    }

    public void setState(ArenaState state, @NotNull Plugin plugin) {
        ArenaState oldState = this.state;
        this.state = state;

        var server = plugin.getServer();
        var event = new ArenaStateChangeEvent(!server.isPrimaryThread(), this, oldState, state);
        server.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            state.callRelevantEvent(this, plugin);
        }
    }

    public Arena clone(World world) {
        var locationsOneClone = new ArrayList<>(teamLocations);
        for (var location : locationsOneClone) {
            for (var loc : location) {
                loc.setWorld(world);
            }
        }
        return new Arena(displayName, locationsOneClone, state, minimumTeamSize, maximumTeamSize);
    }
}
