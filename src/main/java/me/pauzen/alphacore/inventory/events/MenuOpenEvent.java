/*
 *  Created by Filip P. on 7/1/15 11:06 PM.
 */

package me.pauzen.alphacore.inventory.events;

import me.pauzen.alphacore.events.CallableEvent;
import me.pauzen.alphacore.inventory.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class MenuOpenEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Menu   menu;

    public MenuOpenEvent(Player player, Menu menu) {
        this.player = player;
        this.menu = menu;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
