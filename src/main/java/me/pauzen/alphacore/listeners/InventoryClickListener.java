/*
 *  Created by Filip P. on 2/20/15 9:35 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.inventory.InventoryManager;
import me.pauzen.alphacore.inventory.InventoryMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener extends ListenerImplementation {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        InventoryMenu menu = InventoryManager.getManager().getMenu(e.getInventory());
        if (menu != null) {
            menu.process(e);
        }
    }

}
