/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.places.PlaceManager;
import me.pauzen.alphacore.places.events.PlaceMoveEvent;
import me.pauzen.alphacore.players.data.PlayerData;
import me.pauzen.alphacore.players.data.Tracker;
import me.pauzen.alphacore.players.data.events.PlayerLoadEvent;
import me.pauzen.alphacore.teams.Team;
import me.pauzen.alphacore.teams.TeamManager;
import me.pauzen.alphacore.utils.GeneralUtils;
import me.pauzen.alphacore.utils.commonnms.ClientVersion;
import me.pauzen.alphacore.utils.commonnms.EntityPlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class CorePlayer {


    private String           playerName;
    private EntityPlayer     entityPlayer;
    private Team             team;
    private Place            place;
    private Map<String, Object> attributes = new HashMap<>();

    private PlayerData playerData;

    /**
     * String is Tracker id.
     */
    private Map<String, Tracker> trackers = new HashMap<>();

    private Set<Effect>  activeEffects      = new HashSet<>();
    private Set<Ability> activatedAbilities = new HashSet<>();
    
    public void addAttribute(String attributeName, Object attribute) {
        attributes.put(attributeName, attribute);
    }
    
    public void removeAttribute(String attributeName) {
        attributes.remove(attributeName);
    }
    
    public boolean hasAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }
    
    @SuppressWarnings("ALL")
    public <T> T getAttribute(Class<T> attributeType, String attributeName) {
        return (T) attributes.get(attributeName);
    }

    public CorePlayer(Player player) {
        this.playerName = player.getName();
        this.entityPlayer = new EntityPlayer(player);
        load();
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public ClientVersion getClientVersion() {
        return ClientVersion.valueOf(entityPlayer.getPlayerConnection().getVersion());
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
        return this.activeEffects.contains(effect) || place.hasActivated(effect);
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
        return activatedAbilities.contains(ability) || place.hasActivated(ability);
    }
    
    public boolean hasActivated(Class<? extends Ability> abilityClass) {
        for (Ability activatedAbility : activatedAbilities) {
            if (activatedAbility.getClass() == abilityClass) {
                return true;
            }
        }
        
        return false;
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

    public String getUUID() {
        return getPlayer().getUniqueId().toString();
    }

    @Todo("Create loading/saving system.")
    public void load() {
        this.playerData = new PlayerData(this);
        this.team = playerData.getYamlReader().getTeam(this);
        if (this.team == null) {
            this.team = TeamManager.getDefaultTeam();
        }
        List<Tracker> trackers = getPlayerData().getYamlReader().getTrackers(this);
        PlayerLoadEvent playerLoadEvent = new PlayerLoadEvent(this);
        playerLoadEvent.call();
        playerLoadEvent.getDefaultAbilities().forEach(this::activateAbility);
        if (trackers == null) {
            playerLoadEvent.getDefaultTrackers().forEach((tracker) -> tracker.addTracker(this));
        }
        else {
            getPlayerData().getYamlReader().getTrackers(this).forEach(tracker -> tracker.addTracker(this));
        }
        this.place = PlaceManager.getDefaultPlace();
    }

    public void save() {
        this.trackers.entrySet().forEach(entry -> getPlayerData().getYamlWriter().saveTracker(this, entry.getValue()));

        getPlayerData().getYamlBuilder().save();
        //TODO: Create save function that saves to YAML file. Do not save abilities.
    }

    public boolean isOnGround() {
        return getPlayer().getLocation().subtract(0, 0.0001D, 0).getBlock().getType().isSolid();
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
        String[] spamMessage = new String[]{" ", "  ", "   ", "    "};
        for (int n = 0; n < 300 / spamMessage.length; n++) {
            for (String aSpamMessage : spamMessage) {
                getPlayer().sendMessage(aSpamMessage);
            }
        }
    }

    public Map<String, Tracker> getTrackers() {
        return trackers;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public Place getCurrentPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place.removePlayer(this);
        place.addPlayer(this);
        new PlaceMoveEvent(this, this.place, place);
        this.place = place;
    }

    public void feed() {
        getPlayer().setFoodLevel(20);
        getPlayer().setSaturation(20);
    }

    public boolean hasLeft() {
        return getPlayer() == null;
    }

    public void sendJSON(String json) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + getPlayer().getName() + " " + json);
    }
}

