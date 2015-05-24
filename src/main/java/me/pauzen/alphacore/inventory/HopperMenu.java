/*
 *  Created by Filip P. on 5/6/15 5:03 PM.
 */

package me.pauzen.alphacore.inventory;

public abstract class HopperMenu extends InventoryMenu {

    protected HopperMenu(String name) {
        super(name, 0);
        super.size = 5;
    }
}
