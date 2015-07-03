/*
 *  Created by Filip P. on 2/6/15 6:28 PM.
 */

package me.pauzen.alphacore.effects.events;

import me.pauzen.alphacore.effects.AppliedEffect;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.events.CallablePlayerEvent;
import org.bukkit.event.HandlerList;

public class EffectApplyEvent extends CallablePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final AppliedEffect appliedEffect;
    private final Effect        effect;

    public EffectApplyEvent(AppliedEffect appliedEffect, Effect effect) {
        super(appliedEffect.getCorePlayer());
        this.appliedEffect = appliedEffect;
        this.effect = effect;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Effect getEffect() {
        return effect;
    }

    public AppliedEffect getAppliedEffect() {
        return appliedEffect;
    }
}
