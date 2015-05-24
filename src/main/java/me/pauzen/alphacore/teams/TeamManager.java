/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullify;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TeamManager extends ListenerImplementation implements ModuleManager<Team> {

    private static final Team DEFAULT_TEAM = new DefaultTeam();
    @Nullify
    private static TeamManager manager;

    private Map<String, Team> teams = new HashMap<>();
    private TeamSettings settings;

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
        team.getMembers().forEach(team::removePlayer);
        this.teams.remove(team.getName());
    }

    public Team getTeam(CorePlayer corePlayer) {
        return corePlayer.getTeam();
    }

    public Team getTeam(String teamName) {
        return this.teams.get(teamName);
    }

    @Override
    public void onDisable() {
        manager.teams.values().forEach(Team::clean);
    }

    @Override
    public void onEnable() {
        settings = new TeamSettings();
    }

    public TeamSettings getSettings() {
        return settings;
    }

    @Override
    public Collection<Team> getModules() {
        return teams.values();
    }

    @Override
    public void registerModule(Team module) {
        teams.put(module.getName(), module);
    }

    @Override
    public void unregisterModule(Team module) {
        teams.remove(module.getName());
    }

    @Override
    public String getName() {
        return "teams";
    }
}