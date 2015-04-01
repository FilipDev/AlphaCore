/*
 *  Created by Filip P. on 3/14/15 1:39 AM.
 */

package me.pauzen.alphacore.abilities;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class AbilityStateChangeEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Ability ability;
    private boolean state;

    public AbilityStateChangeEvent(CorePlayer corePlayer, Ability ability, boolean state) {
        super(corePlayer);

        this.ability = ability;
        this.state = state;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Ability getAbility() {
        return ability;
    }

    public boolean getState() {
        return state;
    }
}
