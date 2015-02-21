/*
 *  Created by Filip P. on 2/20/15 8:54 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.inventory.misc.ClickType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class InteractableElement extends Element {

    public InteractableElement(Material material) {
        super(material);
    }

    public InteractableElement(ItemStack itemStack) {
        super(itemStack);
    }

    public abstract void onClick(Player clicker, ClickType clickType);

}
