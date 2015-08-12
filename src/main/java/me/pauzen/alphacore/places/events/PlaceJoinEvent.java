/*
 *  Created by Filip P. on 2/10/15 7:13 PM.
 */

package me.pauzen.alphacore.places.events;

import me.pauzen.alphacore.events.CallablePlayerEvent;
import me.pauzen.alphacore.places.places.CorePlace;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class PlaceJoinEvent extends CallablePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private CorePlace place;

    public PlaceJoinEvent(CorePlayer corePlayer, CorePlace place) {
        super(corePlayer);
        this.place = place;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public CorePlace getPlace() {
        return place;
    }
}
