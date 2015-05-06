/*
 *  Created by Filip P. on 2/10/15 7:13 PM.
 */

package me.pauzen.alphacore.places.events;

import me.pauzen.alphacore.events.CallablePlayerEvent;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class PlaceLeaveEvent extends CallablePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Place place;

    public PlaceLeaveEvent(CorePlayer corePlayer, Place place) {
        super(corePlayer);
        this.place = place;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Place getPlace() {
        return place;
    }
}
