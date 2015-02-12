/*
 *  Created by Filip P. on 2/2/15 11:49 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;

import java.util.HashSet;
import java.util.Set;

public class EffectManager implements Registrable {

    @Nullify
    private static EffectManager manager;

    public static void register() {
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
