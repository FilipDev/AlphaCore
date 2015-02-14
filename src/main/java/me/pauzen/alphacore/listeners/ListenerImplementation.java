/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class ListenerImplementation implements Listener {

    public ListenerImplementation() {
        Bukkit.getPluginManager().registerEvents(this, Core.getCore());
    }

    public void unregister(HandlerList handlerList) {
        handlerList.unregister(this);
    }

}
