/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.messages.JSONMessage;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.places.PlaceManager;
import me.pauzen.alphacore.places.events.PlaceMoveEvent;
import me.pauzen.alphacore.players.data.PlayerData;
import me.pauzen.alphacore.players.data.events.PlayerLoadEvent;
import me.pauzen.alphacore.players.data.trackers.Tracker;
import me.pauzen.alphacore.utils.GeneralUtils;
import me.pauzen.alphacore.utils.commonnms.ClientVersion;
import me.pauzen.alphacore.utils.commonnms.EntityPlayer;
import me.pauzen.alphacore.utils.misc.Tickable;
import me.pauzen.alphacore.worlds.border.Border;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class CorePlayer extends PlayerWrapper implements Tickable {

    private final String       playerName;
    private final EntityPlayer entityPlayer;
    private final Map<String, Object>   attributes         = new HashMap<>();
    /**
     * String is Tracker id.
     */
    private final Map<String, Tracker>  trackers           = new HashMap<>();
    private final Map<Effect, Integer>  activeEffects      = new HashMap<>();
    private final Map<Ability, Integer> activatedAbilities = new HashMap<>();
    private Place      place;
    private PlayerData playerData;
    private Inventory  openInventory;

    public CorePlayer(Player player) {
        super(player);
        this.playerName = player.getName();
        this.entityPlayer = new EntityPlayer(player);

    }

    public static CorePlayer get(Player player) {
        return PlayerManager.getManager().getWrapper(player, CorePlayer.class);
    }

    public static List<CorePlayer> getCorePlayers() {
        ArrayList<CorePlayer> players = new ArrayList<>();
        PlayerManager.getManager().getModules().stream().filter((playerWrapper) -> playerWrapper instanceof CorePlayer).forEach((wrapper) -> players.add((CorePlayer) wrapper));
        return players;
    }

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

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
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

    @Deprecated
    public void activateEffect(Effect effect) {
        this.activeEffects.put(effect, 1);
    }

    @Deprecated
    public void activateEffect(Effect effect, int level) {
        this.activeEffects.put(effect, level);
    }

    @Deprecated
    public void deactivateEffect(Effect effect) {
        this.activeEffects.remove(effect);
    }

    public boolean hasActivated(Effect effect) {
        return this.activeEffects.containsKey(effect) || place.hasActivated(effect);
    }

    public Set<Effect> getActiveEffects() {
        return this.activeEffects.keySet();
    }

    @Deprecated
    public boolean setEffectState(Effect effect, boolean newState, int level) {
        return GeneralUtils.setContainment(activeEffects, effect, newState, level);
    }

    @Deprecated
    public boolean toggleEffectState(Effect effect, int level) {
        return GeneralUtils.toggleContainment(activeEffects, effect, level);
    }

    @Deprecated
    public boolean toggleEffectState(Effect effect) {
        return GeneralUtils.toggleContainment(activeEffects, effect, 1);
    }

    public int getLevel(Ability ability) {
        Integer level = activatedAbilities.get(ability);
        return level == null ? 0 : level;
    }

    public int getLevel(Effect effect) {
        Integer level = activeEffects.get(effect);
        return level == null ? 0 : level;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    @Deprecated
    public boolean deactivateAbility(Ability ability) {
        ability.removeAffected(getPlayer().getUniqueId());
        ability.onRemove(this);
        activatedAbilities.remove(ability);
        return false;
    }

    @Deprecated
    public boolean activateAbility(Ability ability, int level) {
        ability.addAffected(getPlayer().getUniqueId());
        ability.onApply(this, level);
        activatedAbilities.put(ability, level);
        return true;
    }

    @Deprecated
    public boolean activateAbility(Ability ability) {
        return activateAbility(ability, 1);
    }

    public boolean hasActivated(Ability ability) {
        return activatedAbilities.containsKey(ability) || place.hasActivated(ability);
    }

    public boolean hasActivated(Class<? extends Ability> abilityClass) {
        for (Ability activatedAbility : activatedAbilities.keySet()) {
            if (activatedAbility.getClass() == abilityClass) {
                return true;
            }
        }

        return false;
    }

    public Set<Ability> getActivatedAbilities() {
        return activatedAbilities.keySet();
    }

    @Deprecated
    public boolean setAbilityState(Ability ability, boolean newState) {
        return GeneralUtils.setContainment(activatedAbilities, ability, newState, 1);
    }

    @Deprecated
    public boolean setAbilityState(Ability ability, boolean newState, int level) {
        return GeneralUtils.setContainment(activatedAbilities, ability, newState, level);
    }

    @Deprecated
    public boolean toggleAbilityState(Ability ability, int level) {
        return GeneralUtils.toggleContainment(activatedAbilities, ability, level);
    }

    @Deprecated
    public boolean toggleAbilityState(Ability ability) {
        return GeneralUtils.toggleContainment(activatedAbilities, ability, 1);
    }

    public String getUUID() {
        return getPlayer().getUniqueId().toString();
    }

    public UUID uuid() {
        return getPlayer().getUniqueId();
    }

    public void load() {
        this.playerData = new PlayerData(this);
        List<Tracker> trackers = getPlayerData().getYamlReader().getTrackers();
        PlayerLoadEvent playerLoadEvent = new PlayerLoadEvent(this);
        playerLoadEvent.call();
        playerLoadEvent.getDefaultAbilities().forEach((ability) -> ability.apply(this));
        if (trackers != null) {
            trackers.forEach(tracker -> tracker.addTracker(this));
        }
        for (Tracker tracker : playerLoadEvent.getDefaultTrackers()) {
            if (!this.getTrackers().containsKey(tracker.getId())) {
                tracker.addTracker(this);
            }
        }
        this.place = PlaceManager.getManager().getPlace(this);
    }

    public void save() {
        this.trackers.entrySet().forEach(entry -> {
            if (entry.getValue().isPersistant()) {
                getPlayerData().getYamlWriter().saveTracker(entry.getValue());
            }
        });
        getPlayerData().getYamlConstructor().save();
        //TODO: Create save function that saves to YAML file. Do not save abilities.
    }

    public boolean isOnGround() {
        return getPlayer().getLocation().subtract(0, 0.0001D, 0).getBlock().getType().isSolid();
    }

    public Channel getNettyChannel() {
        return this.getEntityPlayer().getPlayerConnection().getNettyChannel();
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
        if (this.place != null) {
            this.place.removePlayer(this);
        }
        place.addPlayer(this);
        if (this.place != null) {
            new PlaceMoveEvent(this, this.place, place).call();
        }
        this.place = place;
    }

    public void feed() {
        getPlayer().setFoodLevel(20);
        getPlayer().setSaturation(20);
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public void sendJSON(String json) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + getPlayer().getName() + " " + json);
    }

    public void sendJSON(JSONMessage message) {
        sendJSON(message.getMessage());
    }

    @Override
    public void unload() {
        for (Ability ability : getActivatedAbilities()) {
            ability.remove(this);
        }
        for (Effect effect : getActiveEffects()) {
            effect.remove(this);
        }
        save();
        PlayerManager.getManager().destroyWrapper(getPlayer(), getClass());
    }

    public Inventory getOpenInventory() {
        return openInventory;
    }

    public void setOpenInventory(Inventory openInventory) {
        this.openInventory = openInventory;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public void tick() {
        Border border = getCurrentPlace().getBorder();
        
        if (border == null) {
            return;
        }
        
        if (!border.isWithin(getPlayer())) {
            border.revert(getPlayer());
        }
    }
}

