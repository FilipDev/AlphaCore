/*
 *  Created by Filip P. on 2/10/15 6:57 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.places.events.PlaceJoinEvent;
import me.pauzen.alphacore.places.events.PlaceLeaveEvent;
import me.pauzen.alphacore.places.events.PlaceMoveEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Place {

    private Set<CorePlayer> players = new HashSet<>();

    private String   name;
    private Location spawn;

    private Set<String> allowedCommands = new HashSet<>(), disallowedCommands = new HashSet<>();
    
    @Todo("Put TeamManagers and stuff into a Place, so to separate the place from the entire server." +
            "Place class should be a complete subsection of AlphaCore.")
    public Place(String name, Location spawn) {
        this.name = name;
        this.spawn = spawn;
    }
    
    public void disallowCommands(String... disallowedCommands) {
        Collections.addAll(this.disallowedCommands, disallowedCommands);
    }    
    
    public void allowCommands(String... allowedCommands) {
        Collections.addAll(this.allowedCommands, allowedCommands);
    }

    public Set<String> getDisallowedCommands() {
        return disallowedCommands;
    }

    public Set<String> getAllowedCommands() {
        return allowedCommands;
    }

    public Location getSpawn() {
        return spawn;
    }

    public String getName() {
        return name;
    }

    public Set<CorePlayer> getPlayers() {
        return players;
    }

    public boolean shouldAllow(Command command) {
        if (!allowedCommands.isEmpty()) {
            return allowedCommands.contains(command.getName());
        }
        if (!disallowedCommands.isEmpty()) {
            return !disallowedCommands.contains(command.getName());
        }
        return true;
    }
    
    public void addPlayer(CorePlayer corePlayer) {
        new PlaceJoinEvent(corePlayer, this).call();
        getPlayers().add(corePlayer);
    }
    
    public void removePlayer(CorePlayer corePlayer) {
        new PlaceLeaveEvent(corePlayer, this).call();
        getPlayers().remove(corePlayer);
    }
    
    public void moveTo(CorePlayer corePlayer, Place place) {
        new PlaceMoveEvent(corePlayer, this, place).call();
        removePlayer(corePlayer);
        place.addPlayer(corePlayer);
    }

}
