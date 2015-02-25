/*
 *  Created by Filip P. on 2/23/15 9:39 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.elements.listeners.UpdateListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AnimatedElement extends Element {

    private InventoryMenu  inventoryMenu;
    private UpdateListener updateListener;

    public AnimatedElement(Material material, InventoryMenu inventoryMenu, UpdateListener updateListener, long delay) {
        this(new ItemStack(material), inventoryMenu, updateListener, delay);
    }

    public AnimatedElement(ItemStack itemStack, InventoryMenu inventoryMenu, UpdateListener updateListener, long delay) {
        super(itemStack);
        this.inventoryMenu = inventoryMenu;
        this.updateListener = updateListener;
        startUpdating(delay);
    }
    
    public void startUpdating(long delay) {
        Bukkit.getScheduler().runTaskTimer(Core.getCore(), () -> inventoryMenu.getOpen().forEach((inventory) -> getListener().onUpdate(inventoryMenu, inventory)), 0L, delay);
    }

    public UpdateListener getListener() {
        return updateListener;
    }
}
