/*
 *  Created by Filip P. on 2/23/15 9:39 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.elements.listeners.UpdateListener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AnimatedElement extends Element {

    private InventoryMenu  inventoryMenu;
    private UpdateListener updateListener;

    public AnimatedElement(Material material, InventoryMenu inventoryMenu, UpdateListener updateListener) {
        super(material);
        this.inventoryMenu = inventoryMenu;
        this.updateListener = updateListener;
    }

    public AnimatedElement(ItemStack itemStack, InventoryMenu inventoryMenu, UpdateListener updateListener) {
        super(itemStack);
        this.inventoryMenu = inventoryMenu;
        this.updateListener = updateListener;
    }

    public UpdateListener getListener() {
        return updateListener;
    }
}
