package me.flame.mcmapi.listener;

import lombok.Data;
import me.flame.mcmapi.Minigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

@Data
public class PlayerItemInteractListener implements Listener {
    private final Minigame minigame;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

    }
}
