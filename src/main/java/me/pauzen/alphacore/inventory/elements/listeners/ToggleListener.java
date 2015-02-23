/*
 *  Created by Filip P. on 2/23/15 12:01 AM.
 */

package me.pauzen.alphacore.inventory.elements.listeners;

import org.bukkit.entity.Player;

public interface ToggleListener {

    public void onToggle(Player player, boolean newState);

}
