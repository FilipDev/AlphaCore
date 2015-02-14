/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;

import java.util.HashMap;
import java.util.Map;

public class TeamManager implements Registrable {

    @Nullify
    private static TeamManager manager;

    public static void register() {
        manager = new TeamManager();
    }

    private Map<String, Team> teams = new HashMap<>();

    private static final Team DEFAULT_TEAM = new DefaultTeam();

    public static TeamManager getManager() {
        return manager;
    }

    public void addTeam(Team team) {
        this.teams.put(team.getName(), team);
    }

    public void deleteTeam(Team team) {
        this.teams.remove(team.getName());
    }

    public Team getTeam(CorePlayer corePlayer) {
        return corePlayer.getTeam();
    }
    
    public Team getTeam(String teamName) {
        return this.teams.get(teamName);
    }

    public static Team getDefaultTeam() {
        return DEFAULT_TEAM;
    }

    public void nullify() {
        Registrable.super.nullify();
        manager.teams.values().forEach(Team::cleanup);
    }
}
