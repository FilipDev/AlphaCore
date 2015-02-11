/*
 *  Created by Filip P. on 2/2/15 11:49 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Nullify;

import java.util.HashSet;
import java.util.Set;

public class EffectManager implements Nullifiable {
    
    @Nullify
    private static EffectManager manager;
    
    public static void registerManager() {
        manager = new EffectManager();
        new EffectUpdater();
    }

    public static EffectManager getManager() {
        return manager;
    }

    private Set<Effect> registeredEffects = new HashSet<>();
    
    public void registerEffect(Effect effect) {
        registeredEffects.add(effect);
    }
    
    public Set<Effect> getRegisteredEffects() {
        return registeredEffects;
    }

}
