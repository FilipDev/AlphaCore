package me.pauzen.bukkitcommonpluginapi.players;

import me.pauzen.bukkitcommonpluginapi.PointManager;
import me.pauzen.bukkitcommonpluginapi.abilities.Ability;
import me.pauzen.bukkitcommonpluginapi.playerlogger.PlayerLogger;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class MyPlayer {

    private Set<Ability> activatedAbilities = new HashSet<>();
    private final Player player;
    private PlayerLogger playerLogger;
    private int points;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
        PointManager.getPointManager().update();
    }
    
    public void addPoints(int points) {
        setPoints(getPoints() + points);
    }
    
    public void subtractPoints(int points) {
        setPoints(getPoints() - points);
    }

    public MyPlayer(Player player) {
        this.player = player;
        load();
    }

    public void addMinutePlayTime() {
        playerLogger.addMinute();
    }

    public void registerDefaultAbilities() {
        for (Ability ability : Ability.values()) {
            if (ability.isDefault()) {
                activateAbility(ability);
            }
        }
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
    
    public boolean isActivated(Ability ability) {
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
        registerDefaultAbilities();
        //TODO: Create load function that reads from YAML file.
    }

    public void save() {
        //TODO: Create save function that saves to YAML file. Do not save abilities.
    }
}
