/*
 *  Created by Filip P. on 6/21/15 1:03 AM.
 */

package me.pauzen.alphacore.inventory.elements.buttons;

import me.pauzen.alphacore.inventory.elements.ClickableElement;
import me.pauzen.alphacore.inventory.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ExitButton extends ClickableElement {

    public ExitButton() {
        super((clicker, clickType, inventory, menu) -> {
            clicker.closeInventory();
        }, ItemBuilder.from(new ItemStack(Material.INK_SACK)).durability(1).name(ChatColor.DARK_RED + "Exit").lore(ChatColor.RED + "Exits the menu").build());
    }
    
}
