/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Nullify;

import java.util.HashSet;
import java.util.Set;

public class TeamManager implements Nullifiable {

    @Nullify
    private static TeamManager manager;
    
    public static void registerManager() {
        manager = new TeamManager();
    }

    private Set<Team> teams = new HashSet<>();

    private static final Team DEFAULT_TEAM = new DefaultTeam();

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void deleteTeam(Team team) {
        this.teams.remove(team);
    }

    public Team getTeam(CorePlayer corePlayer) {
        return corePlayer.getTeam();
    }
    
    public static Team getDefaultTeam() {
        return DEFAULT_TEAM;
    }

    public void nullify() {
        Nullifiable.super.nullify();
        manager.teams.forEach(Team::cleanup);
    }
}
