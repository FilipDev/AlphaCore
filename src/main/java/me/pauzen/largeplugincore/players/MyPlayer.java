/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.largeplugincore.players;

import me.pauzen.largeplugincore.abilities.Ability;
import me.pauzen.largeplugincore.playerlogger.PlayerLogger;
import me.pauzen.largeplugincore.points.PointDisplayer;
import me.pauzen.largeplugincore.points.PointManager;
import me.pauzen.largeplugincore.teams.Team;
import me.pauzen.largeplugincore.utils.MyInteger;
import me.pauzen.largeplugincore.utils.commonnms.ClientVersion;
import me.pauzen.largeplugincore.utils.commonnms.EntityPlayer;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class MyPlayer {

    private Set<Ability> activatedAbilities = new HashSet<>();
    
    
    private final Player       player;
    private       PlayerLogger playerLogger;
    private       MyInteger    points;
    private       EntityPlayer entityPlayer;
    private Team team;

    public MyPlayer(Player player) {
        this.player = player;
        this.entityPlayer = new EntityPlayer(player);
        load();
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public ClientVersion getClientVersion() {
        return ClientVersion.valueOf(entityPlayer.getPlayerConnection().getVersion());
    }

    public void addPointDisplayer() {
        new PointDisplayer(this);
    }

    public MyInteger getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points.setValue(points);
        PointManager.getManager().update();
    }
    
    public void addPoints(int points) {
        setPoints(getPoints().getValue() + points);
    }

    public void subtractPoints(int points) {
        setPoints(getPoints().getValue() - points);
    }

    public void addMinutePlayTime() {
        playerLogger.addMinute();
    }

    public void registerDefaultAbilities() {
        Ability.getRegisteredAbilities().stream().filter(Ability::isDefault).forEach(this::activateAbility);
    }
    
    public void assignTeam(Team team) {
        this.team = team;
    }
    
    public Player getPlayer() {
        return this.player;
    }

    public boolean deactivateAbiltiy(Ability ability) {
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
        if (newState) {
            activatedAbilities.add(ability);
        } else {
            activatedAbilities.remove(ability);
        }
        return newState;
    }

    public boolean toggleAbilityState(Ability ability) {
        if (activatedAbilities.contains(ability)) {
            activatedAbilities.remove(ability);
            return false;
        } else {
            activatedAbilities.add(ability);
            return true;
        }
    }
    
    public void load() {
        this.playerLogger = new PlayerLogger(this);
        this.points = new MyInteger(0); //TODO: Load points from YAML file.
        registerDefaultAbilities();
        //TODO: Create load function that reads from YAML file.
    }

    public void save() {
        //TODO: Create save function that saves to YAML file. Do not save abilities.
    }
    
    public boolean isOnGround() {
        return this.player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR;
    }
    
    public Channel getNettyChannel() {
        return this.getEntityPlayer().getPlayerConnection().getNettyChannel();
    }

    public Team getTeam() {
        return team;
    }
}
