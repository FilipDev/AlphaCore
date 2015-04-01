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

    private static final Team DEFAULT_TEAM = new DefaultTeam();
    @Nullify
    private static TeamManager manager;
    private Map<String, Team> teams = new HashMap<>();
    private boolean defaultTeamPreventPVP = false;

    public static void register() {
        manager = new TeamManager();
    }

    public static TeamManager getManager() {
        return manager;
    }

    public static Team getDefaultTeam() {
        return DEFAULT_TEAM;
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

    public void nullify() {
        manager.teams.values().forEach(Team::clean);
        Registrable.super.nullify();
    }

    public boolean getDefaultTeamPreventPVP() {
        return defaultTeamPreventPVP;
    }
}