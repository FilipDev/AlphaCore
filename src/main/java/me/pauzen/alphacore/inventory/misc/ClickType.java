/*
 *  Created by Filip P. on 2/20/15 8:55 PM.
 */

package me.pauzen.alphacore.inventory.misc;

import org.bukkit.event.block.Action;

public enum ClickType {

    LEFT,
    RIGHT,
    OTHER,
    ;
    
    public static ClickType fromAction(Action action) {
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK ? ClickType.LEFT : ClickType.RIGHT;
    }

}
