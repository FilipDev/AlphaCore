/*
 *  Created by Filip P. on 5/25/15 11:47 PM.
 */

package me.pauzen.alphacore.dynamicevents.events;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public interface EventConsumer<E extends Event> extends Listener, Consumer<E> {

    public void accept(E event);

}
