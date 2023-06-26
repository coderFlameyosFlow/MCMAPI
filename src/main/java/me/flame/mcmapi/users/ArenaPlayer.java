package me.flame.mcmapi.users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@EqualsAndHashCode
public class ArenaPlayer {
    private final Player player;

    public ArenaPlayer(Player player) {
        this.player = player;
    }
}
