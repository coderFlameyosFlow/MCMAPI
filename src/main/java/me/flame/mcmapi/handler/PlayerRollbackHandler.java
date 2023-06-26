package me.flame.mcmapi.handler;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRollbackHandler {
    private final Map<UUID, PlayerInventory> inventoryMap;
    private final Map<UUID, GameMode> gameModeMap;

    public PlayerRollbackHandler() {
        this.inventoryMap = new HashMap<>(500);
        this.gameModeMap = new HashMap<>(500);
    }

    public void add(Player player) {
        var uuid = player.getUniqueId();
        this.inventoryMap.put(uuid, player.getInventory());
        this.gameModeMap.put(uuid, player.getGameMode());
    }

    public void remove(Player player) {
        var uuid = player.getUniqueId();
        var inventory = this.inventoryMap.remove(uuid);
        var gameMode = this.gameModeMap.remove(uuid);
        player.getInventory().setContents(inventory.getContents());
        player.setGameMode(gameMode);
    }
}
