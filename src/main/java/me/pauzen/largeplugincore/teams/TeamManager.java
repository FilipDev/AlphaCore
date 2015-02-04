/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.largeplugincore.teams;

import me.pauzen.largeplugincore.players.MyPlayer;

import java.util.HashSet;
import java.util.Set;

public class TeamManager {

    private Set<Team> teams = new HashSet<>();
    
    public void addTeam(Team team) {
        this.teams.add(team);
    }
    
    public void deleteTeam(Team team) {
        this.teams.remove(team);
    }
    
    public Team getTeam(MyPlayer myPlayer) {
        return myPlayer.getTeam();
    }

}
