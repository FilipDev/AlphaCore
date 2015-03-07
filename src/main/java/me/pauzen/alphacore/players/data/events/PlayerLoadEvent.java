/*
 *  Created by Filip P. on 3/1/15 2:01 AM.
 */

package me.pauzen.alphacore.players.data.events;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.Tracker;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

public class PlayerLoadEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private List<Tracker> defaultTrackers  = new ArrayList<>();
    private List<Ability> defaultAbilities = new ArrayList<>();

    public PlayerLoadEvent(CorePlayer corePlayer) {
        super(corePlayer);
    }

    public boolean addDefault(Tracker tracker) {
        return defaultTrackers.add(tracker);
    }
    
    public boolean addDefault(Ability ability) {
        return defaultAbilities.add(ability);
    }

    public List<Tracker> getDefaultTrackers() {
        return defaultTrackers;
    }

    public List<Ability> getDefaultAbilities() {
        return defaultAbilities;
    }
}
