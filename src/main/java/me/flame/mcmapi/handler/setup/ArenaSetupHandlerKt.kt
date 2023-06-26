package me.flame.mcmapi.handler.setup

import me.flame.mcmapi.arena.TemporaryArena
import me.flame.mcmapi.util.menus.GuiAction
import me.flame.mcmapi.util.menus.GuiItem
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*
import java.util.function.Predicate

class ArenaSetupHandlerKt : SetupHandler {
    private val items: MutableList<GuiItem> = ArrayList()
    private val arenas: MutableMap<UUID, TemporaryArena> = HashMap(500)

    override fun addItem(item: GuiItem) {
        items.add(item)
    }

    override fun getItem(predicate: Predicate<GuiItem>): GuiItem? = items.firstOrNull(predicate::test)

    override fun getItem(index: Int): GuiItem? = try { items[index] } catch (e: IndexOutOfBoundsException) { null }

    @EventHandler
    override fun onPlayerInteract(event: PlayerInteractEvent) {
        val itemStack = event.item
        val item = items.firstOrNull { it.item == itemStack }
        item?.action?.run(event)
    }
}