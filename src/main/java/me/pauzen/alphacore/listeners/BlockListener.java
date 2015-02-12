/*
 *  Created by Filip P. on 2/6/15 7:03 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener extends ListenerImplementation {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {

        if (CorePlayer.get(e.getPlayer()).hasActivated(PremadeEffects.NO_BLOCK_BREAK.effect())) {
            e.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {

        if (CorePlayer.get(e.getPlayer()).hasActivated(PremadeEffects.NO_BLOCK_PLACE.effect())) {
            e.setCancelled(true);
        }

    }

}
