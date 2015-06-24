/*
 *  Created by Filip P. on 5/4/15 6:04 PM.
 */

package me.pauzen.alphacore.worlds;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.places.PhysicalPlace;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.server.CoreServer;
import me.pauzen.alphacore.utils.misc.Tickable;
import me.pauzen.alphacore.worlds.border.Border;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public class CoreWorld implements ManagerModule, Tickable {

    private final World world;
    private final Place worldPlace;
    
    private Set<WorldProperty> appliedProperties = new HashSet<>();

    private long lockTime;

    public CoreWorld(World world) {
        this.world = world;
        worldPlace = new PhysicalPlace(world.getName(), CoreServer.SERVER_PLACE) {
            @Override
            public boolean contains(Location location) {
                return location.getWorld() == world;
            }
        };
    }

    public static CoreWorld get(World world) {
        return WorldManager.getManager().getCoreWorld(world);
    }

    public static CoreWorld get(String worldName) {
        return WorldManager.getManager().getCoreWorld(Bukkit.getWorld(worldName));
    }

    public World getWorld() {
        return world;
    }

    public Place getWorldPlace() {
        return worldPlace;
    }

    @Override
    public void unload() {
        WorldManager.getManager().unregisterModule(this);
    }

    public boolean isApplied(WorldProperty property) {
        return appliedProperties.contains(property);
    }

    public void applyProperty(WorldProperty property) {
        appliedProperties.add(property);
    }

    public void removeProperty(WorldProperty property) {
        appliedProperties.remove(property);
    }

    public Set<WorldProperty> getAppliedProperties() {
        return appliedProperties;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }
    
    @Override
    public void tick() {
        if (isApplied(WorldProperty.LOCK_TIME)) {
            getWorld().setTime(getLockTime());
        }
    }

    public void setBorder(Border border) {
        worldPlace.setBorder(border);
    }
    
    public Border getBorder() {
        return worldPlace.getBorder();
    }
}