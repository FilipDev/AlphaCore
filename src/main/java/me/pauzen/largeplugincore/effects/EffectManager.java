/*
 *  Created by Filip P. on 2/2/15 11:49 PM.
 */

package me.pauzen.largeplugincore.effects;

import java.util.HashSet;
import java.util.Set;

public class EffectManager {
    
    private static EffectManager manager;
    
    public static void registerManager() {
        manager = new EffectManager();
    }
    
    public static EffectManager getManager() {
        return manager;
    }

    private Set<Effect> registeredEffects = new HashSet<>();
    
    public void registerEffect(Effect effect) {
        registeredEffects.add(effect);
    }

}
