/*
 *  Created by Filip P. on 2/2/15 11:49 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.utils.reflection.Nullify;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EffectManager implements ModuleManager<Effect> {

    @Nullify
    private static EffectManager manager;
    private Set<Effect> registeredEffects = new HashSet<>();

    public static void register() {
        manager = new EffectManager();
    }
    
    @Override
    public void onEnable() {
        new EffectUpdater();
    }

    public static EffectManager getManager() {
        return manager;
    }

    public Set<Effect> getRegisteredEffects() {
        return registeredEffects;
    }

    @Override
    public String getName() {
        return "effects";
    }

    @Override
    public Collection<Effect> getModules() {
        return registeredEffects;
    }

    @Override
    public void registerModule(Effect effect) {
        registeredEffects.add(effect);
    }

    @Override
    public void unregisterModule(Effect module) {
        registeredEffects.remove(module);
    }
}
