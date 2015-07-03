/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ListenerImplementation implements Listener, ManagerModule {

    public ListenerImplementation() {
        JavaPlugin providingPlugin = JavaPlugin.getProvidingPlugin(getClass());
        Bukkit.getPluginManager().registerEvents(this, providingPlugin);
    }

    public void unregister(HandlerList handlerList) {
        handlerList.unregister(this);
    }

    public void unregister(Event event) {
        event.getHandlers().unregister(this);
    }

    @Override
    public void unload() {
        ReflectionFactory.getMethodsHierarchic(getClass()).stream().filter(method -> method.getAnnotation(EventHandler.class) != null).forEach(method -> {
            Class<? extends Event>[] parameterTypes = (Class<? extends Event>[]) method.getParameterTypes();
            if (parameterTypes.length == 1) {
                Class<? extends Event> parameterType = parameterTypes[0];

                if (Event.class.isAssignableFrom(parameterType)) {
                    Method handlerListMethod = ReflectionFactory.getMethodHierarchic(parameterType, "getHandlerList");
                    try {
                        HandlerList handlerList = (HandlerList) handlerListMethod.invoke(null);
                        unregister(handlerList);
                        System.out.println("AlphaCore: Unregistered EventHandler " + handlerListMethod + " from class " + getClass());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
