/*
 *  Created by Filip P. on 2/23/15 9:41 PM.
 */

package me.pauzen.alphacore.inventory.elements.listeners;

import me.pauzen.alphacore.inventory.InventoryMenu;
import org.bukkit.inventory.Inventory;

public interface UpdateListener {

    public void onUpdate(InventoryMenu menu, Inventory inventory);
}
