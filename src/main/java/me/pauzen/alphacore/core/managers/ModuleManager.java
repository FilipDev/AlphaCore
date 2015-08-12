/*
 *  Created by Filip P. on 2/11/15 5:18 PM.
 */

package me.pauzen.alphacore.core.managers;

import me.pauzen.alphacore.core.modules.ManagerModule;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

public interface ModuleManager<T extends ManagerModule> extends Manager {

    public Collection<T> getModules();

    public void registerModule(T module);

    public void unregisterModule(T module);

    public default void unregister() {
        Collection<T> collection = new ArrayList<>();
        collection.addAll(getModules());
        collection.forEach(this::unregisterModule);
    }
    
    public default void onDisable(Plugin plugin) {
        for (T module : new ArrayList<T>() {{addAll(getModules());}}) {
            if (module.getOwner() == plugin) {
                module.unload();
            }
        }
    }
    
}
