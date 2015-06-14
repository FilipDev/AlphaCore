/*
 *  Created by Filip P. on 3/21/15 4:45 PM.
 */

package me.pauzen.alphacore.emitters;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmitterManager extends ListenerImplementation implements ModuleManager<Emitter> {

    @Nullify
    private static EmitterManager manager;
    private List<Emitter> emitters = new ArrayList<>();

    public static EmitterManager getManager() {
        return manager;
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (event.getUpdateType() == UpdateType.TICK) {
            for (Emitter emitter : emitters) {
                if (emitter.elapsed()) {
                    emitter.emit();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "emmiters";
    }

    @Override
    public Collection<Emitter> getModules() {
        return emitters;
    }

    @Override
    public void registerModule(Emitter module) {
        emitters.add(module);
    }

    @Override
    public void unregisterModule(Emitter module) {
        emitters.remove(module);
    }
}
