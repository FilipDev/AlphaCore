/*
 *  Created by Filip P. on 2/10/15 7:13 PM.
 */

package me.pauzen.alphacore.places.events;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class PlaceMoveEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Place newPlace, oldPlace;

    public PlaceMoveEvent(CorePlayer corePlayer, Place oldPlace, Place newPlace) {
        super(corePlayer);
        this.oldPlace = oldPlace;
        this.newPlace = newPlace;
    }

    public Place getNewPlace() {
        return newPlace;
    }

    public Place getOldPlace() {
        return oldPlace;
    }
}
