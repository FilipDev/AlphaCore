/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.players.CorePlayer;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private static TeamManager manager;
    
    public static void registerManager() {
        manager = new TeamManager();
    }
    
    private Set<Team> teams = new HashSet<>();
    
    public void addTeam(Team team) {
        this.teams.add(team);
    }
    
    public void deleteTeam(Team team) {
        this.teams.remove(team);
    }
    
    public Team getTeam(CorePlayer CorePlayer) {
        return CorePlayer.getTeam();
    }

    public static void unregisterManager() {
        manager.teams.forEach(Team::cleanup);
        manager = null;
    }
}
