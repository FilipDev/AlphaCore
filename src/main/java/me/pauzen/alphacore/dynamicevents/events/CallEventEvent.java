/*
 *  Created by Filip P. on 2/28/15 9:50 AM.
 */

package me.pauzen.alphacore.dynamicevents.events;

import me.pauzen.alphacore.events.CallableEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CallEventEvent<E extends Event> extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();
    private E event;

    public CallEventEvent(E event) {
        this.event = event;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public E getEvent() {
        return event;
    }
}
