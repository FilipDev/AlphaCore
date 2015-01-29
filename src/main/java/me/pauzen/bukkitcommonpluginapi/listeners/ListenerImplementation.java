package me.pauzen.bukkitcommonpluginapi.listeners;

import me.pauzen.bukkitcommonpluginapi.Core;
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
