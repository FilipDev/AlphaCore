/*
 *  Created by Filip P. on 2/2/15 11:13 PM.
 */

package me.pauzen.largeplugincore.updater;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private UpdateType updateType;

    public UpdateEvent(UpdateType updateType) {
        this.updateType = updateType;
    }    
    
    public UpdateType getUpdateType() {
        return updateType;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}
