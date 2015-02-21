/*
 *  Created by Filip P. on 2/20/15 9:31 PM.
 */

package me.pauzen.alphacore.inventory;

import me.pauzen.alphacore.utils.InvisibleID;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements Registrable {

    @Nullify
    private static InventoryManager manager;

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
