/*
 *  Created by Filip P. on 2/20/15 9:31 PM.
 */

package me.pauzen.alphacore.inventory;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.misc.string.InvisibleID;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Priority(LoadPriority.FIRST)
public class InventoryManager extends ListenerImplementation implements ModuleManager<InventoryMenu> {

    @Nullify
    private static InventoryManager manager;

    private Map<String, InventoryMenu> menus = new HashMap<>();
    private boolean                    skip  = false;

    public static InventoryManager getManager() {
        return manager;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        CorePlayer corePlayer = CorePlayer.get((Player) event.getPlayer());

        corePlayer.addAttribute("open_inventory", event.getInventory());

        InventoryMenu menu = (InventoryMenu) getMenu(event.getInventory());

        if (menu != null) {
            menu.openInventory((Player) event.getPlayer(), event.getInventory());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        CorePlayer corePlayer = CorePlayer.get((Player) event.getPlayer());

        corePlayer.removeAttribute("open_inventory");

        InventoryMenu menu = (InventoryMenu) getMenu(event.getInventory());

        if (menu != null) {
            menu.closeInventory((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getRawSlot() == -999) {
            return;
        }

        Menu menu = getMenu(e.getInventory());

        if (menu != null) {
            menu.processClick(e);
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (event.getUpdateType() == UpdateType.TICK) {
            if (!(skip = !skip)) {
                for (CorePlayer corePlayer : CorePlayer.getCorePlayers()) {
                    if (!corePlayer.hasAttribute("open_inventory")) {
                        corePlayer.removeAttribute("previous_menus");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (getMenu(e.getInventory()) != null) {
            e.setCancelled(true);
        }
    }

    @Override
    public void registerModule(InventoryMenu menu) {
        menus.put(menu.getID(), menu);
    }

    public Menu getMenu(Inventory inventory) {

        if (InvisibleID.hasInvisibleID(inventory.getName())) {
            return menus.get(InvisibleID.getIDFrom(inventory.getName()));
        }

        return null;
    }

    @Override
    public String getName() {
        return "inventories";
    }

    @Override
    public Collection<InventoryMenu> getModules() {
        return menus.values();
    }

    @Override
    public void unregisterModule(InventoryMenu module) {
        menus.remove(module.getID());
    }
}
