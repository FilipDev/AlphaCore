/*
 *  Created by Filip P. on 2/13/15 1:06 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.events.CallablePlayerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

public class EfficientMoveEvent extends CallablePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private PlayerMoveEvent event;

    public EfficientMoveEvent(PlayerMoveEvent event, CorePlayer corePlayer) {
        super(corePlayer);
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public PlayerMoveEvent getEvent() {
        return event;
    }
}
