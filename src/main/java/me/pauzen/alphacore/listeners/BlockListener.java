/*
 *  Created by Filip P. on 2/6/15 7:03 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            e.setCancelled(leftClickBlock(e));
        }
        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            e.setCancelled(leftClickAir(e));
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            e.setCancelled(rightClickAir(e));
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.setCancelled(rightClickBlock(e));
        }
    }

    public boolean leftClickBlock(PlayerInteractEvent e) {
        CorePlayer corePlayer = CorePlayer.get(e.getPlayer());

        if (corePlayer.hasActivated(PremadeAbilities.INSTANT_BREAK.ability())) {
            e.getClickedBlock().setType(Material.AIR);
            return true;
        }

        return false;
    }

    public boolean rightClickBlock(PlayerInteractEvent e) {
        return false;
    }

    public boolean leftClickAir(PlayerInteractEvent e) {
        return false;
    }

    public boolean rightClickAir(PlayerInteractEvent e) {
        return false;
    }

}
