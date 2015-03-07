/*
 *  Created by Filip P. on 2/20/15 9:31 PM.
 */

package me.pauzen.alphacore.inventory;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.utils.InvisibleID;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

@Priority(LoadPriority.FIRST)
public class InventoryManager extends ListenerImplementation implements Registrable {

    @Nullify
    private static InventoryManager manager;
    
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        InventoryMenu menu = getMenu(e.getInventory());

        if (menu != null) {
            menu.openInventory((Player) e.getPlayer(), e.getInventory());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        InventoryMenu menu = getMenu(e.getInventory());

        if (menu != null) {
            menu.closeInventory((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        InventoryMenu menu = getMenu(e.getInventory());
        
        if (menu != null) {
            menu.process(e);
        }
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (getMenu(e.getInventory()) != null) {
            e.setCancelled(true);
        }
    }

    private Map<String, InventoryMenu> menus = new HashMap<>();

    public static void register() {
        manager = new InventoryManager();
        
    }

    public void registerMenu(InventoryMenu menu) {
        menus.put(menu.getID(), menu);
    }

    public InventoryMenu getMenu(Inventory inventory) {

        if (InvisibleID.hasInvisibleID(inventory.getName())) {
            return menus.get(InvisibleID.getIDFrom(inventory.getName()));
        }

        return null;
    }

    public static InventoryManager getManager() {
        return manager;
    }
}
