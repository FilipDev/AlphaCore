/*
 *  Created by Filip P. on 2/6/15 6:28 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class EffectGetEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Effect effect;

    public EffectGetEvent(CorePlayer CorePlayer, Effect effect) {
        super(CorePlayer);
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
