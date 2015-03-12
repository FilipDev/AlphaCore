/*
 *  Created by Filip P. on 3/12/15 12:05 AM.
 */

package me.pauzen.alphacore.inventory.misc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ElementInteraction {

    private Player clicker;
    private Inventory inventory;

    public ElementInteraction(Player clicker, Inventory inventory) {
        this.clicker = clicker;
        this.inventory = inventory;
    }

    public Player getClicker() {
        return clicker;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
