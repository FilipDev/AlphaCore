/*
 *  Created by Filip P. on 3/1/15 2:01 AM.
 */

package me.pauzen.alphacore.players.data.events;

import me.pauzen.alphacore.events.CallablePlayerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.trackers.Tracker;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoadEvent extends CallablePlayerEvent {

    private static final HandlerList   handlers        = new HandlerList();
    private              List<Tracker> defaultTrackers = new ArrayList<>();

    public PlayerLoadEvent(CorePlayer corePlayer) {
        super(corePlayer);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean addDefault(Tracker tracker) {
        return defaultTrackers.add(tracker);
    }

    public List<Tracker> getDefaultTrackers() {
        return defaultTrackers;
    }
}
