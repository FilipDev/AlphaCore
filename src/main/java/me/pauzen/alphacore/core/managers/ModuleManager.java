/*
 *  Created by Filip P. on 2/11/15 5:18 PM.
 */

package me.pauzen.alphacore.core.managers;

import me.pauzen.alphacore.core.modules.ManagerModule;

import java.util.Collection;

public interface ModuleManager<T extends ManagerModule> extends Manager {

    public Collection<T> getModules();

    public void registerModule(T module);

    public void unregisterModule(T module);

    public default void unregister() {
        getModules().forEach(T::unload);
    }
}
