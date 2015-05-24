/*
 *  Created by Filip P. on 5/21/15 12:19 AM.
 */

package me.pauzen.alphacore.emitters;

import org.bukkit.Effect;

public class EffectEmitter extends Emitter {
    
    private Effect effect;

    public EffectEmitter(Effect effect) {
        this.effect = effect;
    }

    @Override
    public void emit() {
        getLocation().getWorld().playEffect(getLocation(), effect, 1);
    }
}
