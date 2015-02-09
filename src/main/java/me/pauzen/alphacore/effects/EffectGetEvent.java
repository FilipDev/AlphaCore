/*
 *  Created by Filip P. on 2/6/15 6:28 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;

public class EffectGetEvent extends CallablePlayerContainerEvent {

    private Effect effect;

    public EffectGetEvent(CorePlayer CorePlayer, Effect effect) {
        super(CorePlayer);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
