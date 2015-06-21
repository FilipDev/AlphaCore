/*
 *  Created by Filip P. on 6/20/15 10:06 PM.
 */

package me.pauzen.alphacore.inventory.elements.buttons;

import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.Menu;
import me.pauzen.alphacore.inventory.elements.ClickableElement;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TransfererButton extends ClickableElement {

    public TransfererButton(ItemStack itemStack, Menu menu) {
        super((clicker, clickType, inventory, previousMenu) -> {
            Map<String, Object> attributes = CorePlayer.get(clicker).getAttributes();
            attributes.putIfAbsent("previous_menus", new HashMap<InventoryMenu, InventoryMenu>());
            Map<Menu, Menu> previousMenus = (Map<Menu, Menu>) attributes.get("previous_menus");
            previousMenus.put(menu, previousMenu);
            menu.show(clicker);
        }, itemStack);
    }
}
