/*
 *  Created by Filip P. on 2/12/15 12:49 AM.
 */

package me.pauzen.alphacore.places.misc;

import org.bukkit.event.Event;

public class EventContainer<E> {

    private Class<E> eventType;
    private Event    e;

    public EventContainer(Class<E> eventType, Event e) {
        this.eventType = eventType;
        this.e = e;
    }

    public E event() {
        return eventType.cast(e);

    }
}
