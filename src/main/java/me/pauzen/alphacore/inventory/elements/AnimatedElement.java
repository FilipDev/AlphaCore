/*
 *  Created by Filip P. on 2/23/15 9:39 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.inventory.Menu;
import me.pauzen.alphacore.inventory.elements.listeners.UpdateListener;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AnimatedElement extends Element {

    private Menu           menu;
    private UpdateListener updateListener;
    private Coordinate     location;

    public AnimatedElement(Material material, Menu menu, UpdateListener updateListener, Coordinate location) {
        super(material);
        this.menu = menu;
        this.updateListener = updateListener;
        this.location = location;
    }

    public AnimatedElement(ItemStack itemStack, Menu menu, UpdateListener updateListener, Coordinate location) {
        super(itemStack);
        this.menu = menu;
        this.updateListener = updateListener;
        this.location = location;
    }

    public AnimatedElement(Material material, Menu menu, UpdateListener updateListener, long delay) {
        this(new ItemStack(material), menu, updateListener, delay);
    }

    public AnimatedElement(ItemStack itemStack, Menu menu, UpdateListener updateListener, long delay) {
        super(itemStack);
        this.menu = menu;
        this.updateListener = updateListener;
        startUpdating(delay);
    }

    public void startUpdating(long delay) {
        Bukkit.getScheduler().runTaskTimer(Core.getCore(), () -> menu.getOpen().forEach((inventory) -> getListener().onUpdate(menu, inventory, inventory.getItem(location.toSlot()))), 0L, delay);
    }

    public UpdateListener getListener() {
        return updateListener;
    }
}
