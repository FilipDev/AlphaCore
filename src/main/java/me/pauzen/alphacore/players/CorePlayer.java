/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.border.Border;
import me.pauzen.alphacore.effects.Effects;
import me.pauzen.alphacore.messages.JSONMessage;
import me.pauzen.alphacore.places.CorePlace;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.places.PlaceManager;
import me.pauzen.alphacore.places.events.PlaceMoveEvent;
import me.pauzen.alphacore.players.data.PlayerData;
import me.pauzen.alphacore.players.data.events.PlayerLoadEvent;
import me.pauzen.alphacore.players.data.trackers.Tracker;
import me.pauzen.alphacore.utils.commonnms.ClientVersion;
import me.pauzen.alphacore.utils.commonnms.EntityPlayer;
import me.pauzen.alphacore.utils.misc.Tickable;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CorePlayer extends PlayerWrapper implements Tickable {

    private final String       playerName;
    private final EntityPlayer entityPlayer;
    private final Map<String, Object>  attributes = new HashMap<>();
    /**
     * String is Tracker id.
     */
    private final Map<String, Tracker> trackers   = new HashMap<>();
    private Place  place;
    private PlayerData playerData;

    private Effects effects;

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

    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    public String getUUID() {
        return getPlayer().getUniqueId().toString();
    }

    public Channel getNettyChannel() {
        return this.getEntityPlayer().getPlayerConnection().getNettyChannel();
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
            ((CorePlace) this.place).removePlayer(this);
        }
        ((CorePlace) place).addPlayer(this);
        if (this.place != null) {
            new PlaceMoveEvent(this, this.place, place).call();
        }
        this.place = place;
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
    public void save() {
        this.trackers.entrySet().forEach(entry -> {
            if (entry.getValue().isPersistant()) {
                getPlayerData().getYamlWriter().saveTracker(entry.getValue());
            }
        });
        getPlayerData().getYamlConstructor().save();
    }

    @Override
    public void load() {
        this.playerData = new PlayerData(this);
        effects = new Effects(this);
        List<Tracker> trackers = getPlayerData().getYamlReader().getTrackers();
        PlayerLoadEvent playerLoadEvent = new PlayerLoadEvent(this);
        playerLoadEvent.call();
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

    @Override
    public void unload() {
        getEffects().removeAll();
        save();
        PlayerManager.getManager().destroyWrapper(getPlayer(), getClass());
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

    public Effects getEffects() {
        return effects;
    }
}

