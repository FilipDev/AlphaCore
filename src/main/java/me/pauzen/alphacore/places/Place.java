/*
 *  Created by Filip P. on 2/10/15 6:57 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.places.events.PlaceJoinEvent;
import me.pauzen.alphacore.places.events.PlaceLeaveEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.AllowanceChecker;
import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

@Todo("Add more responsibilities for the Place class." +
        "Create way to check if pvp, mob spawning, block modification, and such should be allowed in place.")
public abstract class Place {

    private Set<CorePlayer> players = new HashSet<>();

    private String name;

    private AllowanceChecker<String>      commandChecker     = new AllowanceChecker<>();
    private AllowanceChecker<PlaceAction> placeActionChecker = new AllowanceChecker<>();

    public Place(String name) {
        this.name = name;
    }

    public AllowanceChecker<String> getCommandChecker() {
        return commandChecker;
    }
    
    public boolean shouldRun(Command command) {
        return commandChecker.isAllowed(command.getName());
    }

    public String getName() {
        return name;
    }

    public Set<CorePlayer> getPlayers() {
        return players;
    }

    public void addPlayer(CorePlayer corePlayer) {
        new PlaceJoinEvent(corePlayer, this).call();
        getPlayers().add(corePlayer);
    }

    public void removePlayer(CorePlayer corePlayer) {
        new PlaceLeaveEvent(corePlayer, this).call();
        getPlayers().remove(corePlayer);
    }

    public abstract boolean contains(Location location);

    public boolean isAllowed(PlaceAction placeAction) {
        return placeActionChecker.isAllowed(placeAction);
    }

    public AllowanceChecker<PlaceAction> getPlaceActionChecker() {
        return placeActionChecker;
    }
}