/*
 *  Created by Filip P. on 2/27/15 11:25 PM.
 */

package me.pauzen.alphacore.tools;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.Interactable;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Tool {

    public static Tool EMPTY_TOOL = new Tool(null, null);

    private Interactable<PlayerInteractEvent> listener;
    private ItemStack                         itemStack;

    public Tool(Interactable<PlayerInteractEvent> listener, ItemStack itemStack) {
        this.listener = listener;
        this.itemStack = itemStack;
    }

    public Interactable<PlayerInteractEvent> getListener() {
        return listener;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void give(CorePlayer corePlayer, int slot) {
        corePlayer.getPlayer().getInventory().setItem(slot, itemStack);
    }
    
}
