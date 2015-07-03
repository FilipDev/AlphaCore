/*
 *  Created by Filip P. on 7/1/15 10:43 PM.
 */

package me.pauzen.alphacore.inventory.events;

import me.pauzen.alphacore.events.CallableEvent;
import me.pauzen.alphacore.inventory.Menu;
import me.pauzen.alphacore.inventory.elements.Element;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ElementClickEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Element clicked;
    private final Player  player;
    private final Menu    menu;

    public ElementClickEvent(Element clicked, Player player, Menu menu) {
        this.clicked = clicked;
        this.player = player;
        this.menu = menu;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Element getClicked() {
        return clicked;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {
        return menu;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
