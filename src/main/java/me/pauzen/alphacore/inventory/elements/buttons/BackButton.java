/*
 *  Created by Filip P. on 6/20/15 8:02 PM.
 */

package me.pauzen.alphacore.inventory.elements.buttons;

import me.pauzen.alphacore.inventory.Menu;
import me.pauzen.alphacore.inventory.elements.ClickableElement;
import me.pauzen.alphacore.inventory.items.ItemBuilder;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BackButton extends ClickableElement {

    public BackButton() {
        super((clicker, clickType, inventory, menu) -> {
            CorePlayer corePlayer = CorePlayer.get(clicker);
            if (corePlayer.hasAttribute("previous_menus")) {
                Map<Menu, Menu> previousMenus = corePlayer.getAttribute(Map.class, "previous_menus");
                if (previousMenus.containsKey(menu)) {
                    previousMenus.get(menu).show(clicker);
                    previousMenus.remove(menu);
                }
            }

        }, ItemBuilder.from(new ItemStack(Material.INK_SACK)).durability(14).name(ChatColor.GOLD + "Return").lore(ChatColor.YELLOW + "Return to the previous menu").build());
    }
}
