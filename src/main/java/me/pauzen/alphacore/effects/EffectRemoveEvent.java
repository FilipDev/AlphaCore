/*
 *  Created by Filip P. on 2/6/15 6:31 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.events.CallablePlayerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class EffectRemoveEvent extends CallablePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Effect effect;

    public EffectRemoveEvent(CorePlayer CorePlayer, Effect effect) {
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
