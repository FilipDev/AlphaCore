/*
 *  Created by Filip P. on 2/27/15 11:25 PM.
 */

package me.pauzen.alphacore.tools;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.tools.listeners.ToolUseListener;
import org.bukkit.inventory.ItemStack;

public class Tool {

    public static Tool EMPTY_TOOL = new Tool(null, null);

    private ToolUseListener listener;
    private ItemStack       itemStack;

    public Tool(ToolUseListener listener, ItemStack itemStack) {
        this.listener = listener;
        this.itemStack = itemStack;
    }

    public ToolUseListener getListener() {
        return listener;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void give(CorePlayer corePlayer, int slot) {
        corePlayer.getPlayer().getInventory().setItem(slot, itemStack);
    }
    
}
