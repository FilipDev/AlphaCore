/*
 *  Created by Filip P. on 2/10/15 6:57 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.border.Border;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.places.events.PlaceJoinEvent;
import me.pauzen.alphacore.places.events.PlaceLeaveEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.AllowanceChecker;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class CorePlace implements Place {

    private final String name;
    private Place superPlace;
    private Set<CorePlayer> players = new HashSet<>();
    private AllowanceChecker<String>      commandChecker     = new AllowanceChecker<>();
    private AllowanceChecker<PlaceAction> placeActionChecker = new AllowanceChecker<>();
    private Border border;

    public CorePlace(String name, Place superPlace) {
        if (superPlace == null) {
            if (!name.equals("Server")) {
                superPlace = Place.getServerPlace();
            }
        }
        this.name = name;
        this.superPlace = superPlace;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
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

    @Override
    public boolean isAllowed(Command command) {
        for (String name : command.getNames()) {
            if (!commandChecker.isAllowed(name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAllowed(PlaceAction placeAction) {
        Stack<Place> placeStack = new Stack<>();
        Place currentPlace = this;
        while (currentPlace != null) {
            placeStack.add(currentPlace);
            currentPlace = currentPlace.getSuperPlace();
        }

        boolean allowed = true;
        for (Place place : placeStack) {
            if (place.getPlaceActionChecker().allowed(placeAction)) {
                allowed = false;
            }
            if (place.getPlaceActionChecker().disallowed(placeAction)) {
                allowed = false;
            }
        }
        return allowed;
    }

    @Override
    public void unload() {
        PlaceManager.getManager().unregisterModule(this);
    }

    @Override
    public Place getSuperPlace() {
        return superPlace;
    }

    @Override
    public Border getBorder() {
        return border;
    }

    @Override
    public void setBorder(Border border) {
        this.border = border;
    }

    @Override
    public AllowanceChecker<PlaceAction> getPlaceActionChecker() {
        return placeActionChecker;
    }

    @Override
    public AllowanceChecker<String> getCommandChecker() {
        return commandChecker;
    }
}