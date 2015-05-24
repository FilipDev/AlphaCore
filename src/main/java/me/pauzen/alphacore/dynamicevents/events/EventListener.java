/*
 *  Created by Filip P. on 5/20/15 6:01 PM.
 */

package me.pauzen.alphacore.dynamicevents.events;

import org.bukkit.event.Listener;

public interface EventListener<T> extends Listener {
    
    public void onCall(T event);
    
}
