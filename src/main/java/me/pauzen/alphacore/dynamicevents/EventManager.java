/*
 *  Created by Filip P. on 2/28/15 9:41 AM.
 */

/*
 *  Created by Filip P. on 2/11/15 11:37 PM.
 */

package me.pauzen.alphacore.dynamicevents;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.dynamicevents.events.CallEventEvent;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@Priority(LoadPriority.FIRST)
public class EventManager implements Listener, Registrable {

    @Nullify
    private static EventManager manager;
    
    public static void register() {
        manager = new EventManager();
    }
    
    public static <E extends Event> void registerEvent(Class<E> eventClass) {
        manager.registerEventClass(eventClass);
    }
    
    public <E extends Event> void registerEventClass(Class<E> eventClass) {
        Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.HIGHEST, (listener, event) -> this.onEvent(event), Core.getCore());
    }

    private void onEvent(Event event) {
        if (event instanceof CallEventEvent) {
            return;
        }
     
        new CallEventEvent<>(event).call();
    }
}