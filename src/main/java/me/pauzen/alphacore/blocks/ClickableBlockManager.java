/*
 *  Created by Filip P. on 3/22/15 9:30 PM.
 */

package me.pauzen.alphacore.blocks;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClickableBlockManager extends ListenerImplementation implements ModuleManager<ClickableBlock> {

    @Nullify
    private static ClickableBlockManager manager;
    private Map<Location, ClickableBlock> clickableBlockMap = new HashMap<>();

    public static ClickableBlockManager getManager() {
        return manager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (e.getClickedBlock() == null) {
            return;
        }

        if (!e.getClickedBlock().getType().isSolid()) {
            return;
        }

        ClickableBlock clickableBlock = clickableBlockMap.get(e.getClickedBlock().getLocation());

        if (clickableBlock != null) {

            e.setCancelled(true);

            UUID uniqueId = e.getPlayer().getUniqueId();
            if (clickableBlock.shouldClick(uniqueId)) {
                clickableBlock.onClick(ClickType.fromAction(e.getAction()), CorePlayer.get(e.getPlayer()), e);
                clickableBlock.addClickTime(uniqueId);
            }
        }
    }

    @Override
    public String getName() {
        return "click_block";
    }

    @Override
    public Collection<ClickableBlock> getModules() {
        return clickableBlockMap.values();
    }

    @Override
    public void registerModule(ClickableBlock module) {
        clickableBlockMap.put(module.getLocation(), module);
    }

    @Override
    public void unregisterModule(ClickableBlock module) {
        clickableBlockMap.remove(module.getLocation());
    }

}
