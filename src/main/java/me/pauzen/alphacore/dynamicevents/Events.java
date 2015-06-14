/*
 *  Created by Filip P. on 5/25/15 11:47 PM.
 */

package me.pauzen.alphacore.dynamicevents;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.core.managers.Manager;
import me.pauzen.alphacore.dynamicevents.events.EventConsumer;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

@Priority(LoadPriority.FIRST)
public class Events implements Manager {

    @Nullify
    private static Events manager;

    public static <E extends Event> void addExecutor(Class<E> eventClass, EventConsumer<E> listener) {
        Bukkit.getPluginManager().registerEvent(eventClass, listener, EventPriority.HIGHEST, (listener1, event) -> listener.accept((E) event), Core.getCallerPlugin());
    }

    public static <E extends Event> void addExecutor(Class<E> eventClass, EventConsumer<E> listener, EventPriority priority) {
        Bukkit.getPluginManager().registerEvent(eventClass, listener, priority, (listener1, event) -> listener.accept((E) event), Core.getCallerPlugin());
    }

    public String getName() {
        return "dynamic_events";
    }
}
