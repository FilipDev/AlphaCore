/*
 *  Created by Filip P. on 2/22/15 11:59 PM.
 */

package me.pauzen.alphacore.inventory.elements.listeners;

import me.pauzen.alphacore.inventory.Menu;
import me.pauzen.alphacore.inventory.misc.ClickType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface ClickListener {

    public void onClick(Player clicker, ClickType clickType, Inventory inventory, Menu menu);

}
