/*
 *  Created by Filip P. on 2/6/15 6:31 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;

public class EffectRemoveEvent extends CallablePlayerContainerEvent {

    private Effect effect;

    public EffectRemoveEvent(CorePlayer CorePlayer, Effect effect) {
        super(CorePlayer);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
