/*
 *  Created by Filip P. on 2/6/15 6:28 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class EffectGetEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Effect effect;

    public EffectGetEvent(CorePlayer CorePlayer, Effect effect) {
        super(CorePlayer);
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
}
