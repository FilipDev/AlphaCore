/*
 *  Created by Filip P. on 3/22/15 9:30 PM.
 */

package me.pauzen.alphacore.blocks;

import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClickableBlockManager implements Registrable {

    @Nullify
    private static ClickableBlockManager manager;
    private Map<Location, ClickableBlock> clickableBlockMap = new HashMap<>();
    private Map<UUID, Long>               clickTimeMap      = new HashMap<>();
    private long    cooldown    = 200; //4 ticks
    private boolean alwaysReset = true;

    public static void register() {
        manager = new ClickableBlockManager();
    }

    public static ClickableBlockManager getManager() {
        return manager;
    }

    public static void registerBlock(ClickableBlock clickableBlock) {
        getManager().clickableBlockMap.put(clickableBlock.getLocation(), clickableBlock);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (!e.getClickedBlock().getType().isSolid()) {
            return;
        }

        clickTimeMap.putIfAbsent(e.getPlayer().getUniqueId(), System.currentTimeMillis());

        boolean ran = false;

        if (System.currentTimeMillis() - clickTimeMap.get(e.getPlayer().getUniqueId()) < cooldown) {

            ClickableBlock clickableBlock = clickableBlockMap.get(e.getClickedBlock().getLocation());

            if (clickableBlock != null) {
                clickableBlock.onClick(ClickType.fromAction(e.getAction()), CorePlayer.get(e.getPlayer()));
            }

            ran = true;
        }

        if (alwaysReset || ran) {
            clickTimeMap.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

}
