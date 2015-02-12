/*
 *  Created by Filip P. on 2/2/15 11:13 PM.
 */

package me.pauzen.alphacore.updater;

import me.pauzen.alphacore.events.CallableEvent;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private UpdateType updateType;

    public UpdateEvent(UpdateType updateType) {
        this.updateType = updateType;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }
}
