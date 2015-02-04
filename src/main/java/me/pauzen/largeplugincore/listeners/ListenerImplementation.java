/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.listeners;

import me.pauzen.largeplugincore.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ListenerImplementation implements Listener {
    
    public ListenerImplementation() {
        Bukkit.getPluginManager().registerEvents(this, Core.getCore());
    }
    
    public static void registerListeners() {
        new EntityDamageEventListener();
    }
}
