package me.flame.mcmapi.arena;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

@Data
public class TemporaryArena {
    private final String displayName;
    private final Set<Location> teamOneLocations;
    private final Set<Location> teamTwoLocations;
    private final int minimumTeamSize;
    private final int maximumTeamSize;

    public TemporaryArena(Arena arena) {
        this.displayName = arena.getDisplayName();
        this.teamOneLocations = arena.getTeamOneLocations();
        this.teamTwoLocations = arena.getTeamTwoLocations();
        this.minimumTeamSize = arena.getMinimumTeamSize();
        this.maximumTeamSize = arena.getMaximumTeamSize();
    }

    public TemporaryArena(String displayName) {
        this.displayName = displayName;
        this.teamOneLocations = new HashSet<>(50);
        this.teamTwoLocations = new HashSet<>(50);
        this.minimumTeamSize = 0;
        this.maximumTeamSize = 0;
    }
}
