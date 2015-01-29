package me.pauzen.bukkitcommonpluginapi.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MyCallableEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    
    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public boolean isCancelled(){
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean set){
        this.cancelled = set;
    }
    
    public MyCallableEvent call() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }
    
}
