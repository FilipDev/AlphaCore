/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.playerlogger.PlayTimeLogger;
import me.pauzen.alphacore.players.data.DefaultTrackers;
import me.pauzen.alphacore.players.data.PlayerData;
import me.pauzen.alphacore.players.data.Tracker;
import me.pauzen.alphacore.points.TrackerDisplayer;
import me.pauzen.alphacore.teams.Team;
import me.pauzen.alphacore.teams.TeamManager;
import me.pauzen.alphacore.utils.GeneralUtils;
import me.pauzen.alphacore.utils.commonnms.ClientVersion;
import me.pauzen.alphacore.utils.commonnms.EntityPlayer;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CorePlayer {

    private Set<Ability> activatedAbilities = new HashSet<>();

    private String         playerName;
    private PlayTimeLogger playTimeLogger;
    private EntityPlayer   entityPlayer;
    private Team           team;
    private Place          place;

    private PlayerData playerData;

    /**
     * String is Tracker id.
     */
    private Map<String, Tracker> trackers = new HashMap<>();

    private Set<Effect> activeEffects = new HashSet<>();

    public CorePlayer(Player player) {
        this.playerName = player.getName();
        this.entityPlayer = new EntityPlayer(player);
        for (DefaultTrackers tracker : DefaultTrackers.values()) {
            tracker.tracker().addTracker(this);
        }
        load();
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public ClientVersion getClientVersion() {
        return ClientVersion.valueOf(entityPlayer.getPlayerConnection().getVersion());
    }

    public void addPointDisplayer() {
        new TrackerDisplayer(getCurrentPlace(), this, getTracker("kills"));
    }

    public Tracker getTracker(String trackerName) {
        return trackers.get(trackerName);
    }

    public void activateEffect(Effect effect) {
        this.activeEffects.add(effect);
    }

    public void deactivateEffect(Effect effect) {
        this.activeEffects.remove(effect);
    }

    public boolean hasActivated(Effect effect) {
        return this.activeEffects.contains(effect);
    }
    
    public Set<Effect> getActiveEffects() {
        return this.activeEffects;
    }

    public boolean setEffectState(Effect effect, boolean newState) {
        return GeneralUtils.setContainment(activeEffects, effect, newState);
    }

    public boolean toggleEffectState(Effect effect) {
        return GeneralUtils.toggleContainment(activeEffects, effect);
    }

    public void registerDefaultAbilities() {
        Ability.getRegisteredAbilities().stream().filter(Ability::isDefault).forEach(this::activateAbility);
    }
    
    public void assignTeam(Team team) {
        this.team = team;
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    public boolean deactivateAbility(Ability ability) {
        activatedAbilities.remove(ability);
        return false;
    }
    
    public boolean activateAbility(Ability ability) {
        activatedAbilities.add(ability);
        return true;
    }  
    
    public boolean hasActivated(Ability ability) {
        return activatedAbilities.contains(ability);
    }

    public Set<Ability> getActivatedAbilities() {
        return activatedAbilities;
    }
    
    public boolean setAbilityState(Ability ability, boolean newState) {
        return GeneralUtils.setContainment(activatedAbilities, ability, newState);
    }

    public boolean toggleAbilityState(Ability ability) {
        return GeneralUtils.toggleContainment(activatedAbilities, ability);
    }
    
    public void load() {
        this.team = TeamManager.getDefaultTeam();
        getPlayerData().getYamlReader().getTrackers(this).forEach(tracker -> tracker.addTracker(this));
        registerDefaultAbilities();
        //TODO: Create load function that reads from YAML file.
    }
    
    public String getUUID() {
        return getPlayer().getUniqueId().toString();
    }
    
    public void defaultLoad() {
        this.team = TeamManager.getDefaultTeam();
        new PlayTimeLogger(0).addTracker(this);
        registerDefaultAbilities();
    }

    public void save() {
        this.trackers.entrySet().forEach(entry -> getPlayerData().getYamlWriter().saveTracker(this, entry.getValue()));
        
        getPlayerData().getYamlBuilder().save();
        //TODO: Create save function that saves to YAML file. Do not save abilities.
    }
    
    public boolean isOnGround() {
        return getPlayer().getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR;
    }
    
    public Channel getNettyChannel() {
        return this.getEntityPlayer().getPlayerConnection().getNettyChannel();
    }

    public Team getTeam() {
        return team;
    }
    
    public static CorePlayer get(Player player) {
        return PlayerManager.getManager().getWrapper(player);
    }
    
    public void setHealthPercentage(double percentage) {
        getPlayer().setHealth((getPlayer().getMaxHealth() * (percentage / 100)));
    }
    
    public void healFully() {
        setHealthPercentage(100);
    }
    
    public void clearChat() {
        String[] spamMessage = new String[]{" ", "  ", "   ", "==CLEARING CHAT=="};
            for (int n = 0; n < 50; n++) {
                for (String aSpamMessage : spamMessage) {
                    getPlayer().sendMessage(aSpamMessage);
                }
        }
    }

    public Map<String,Tracker> getTrackers() {
        return trackers;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Place getCurrentPlace() {
        return place;
    }
}

