/*
 *  Created by Filip P. on 2/23/15 9:41 PM.
 */

package me.pauzen.alphacore.inventory.elements.listeners;

import me.pauzen.alphacore.inventory.Menu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface UpdateListener {

    public void onUpdate(Menu menu, Inventory inventory, ItemStack itemStack);
}
