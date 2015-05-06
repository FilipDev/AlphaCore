/*
 *  Created by Filip P. on 5/4/15 6:04 PM.
 */

package me.pauzen.alphacore.worlds;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.places.PhysicalPlace;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.server.CoreServer;
import org.bukkit.Location;
import org.bukkit.World;

public class CoreWorld implements ManagerModule {

    private final World world;
    private final Place worldPlace;

    public CoreWorld(World world) {
        this.world = world;
        worldPlace = new PhysicalPlace(world.getName(), CoreServer.SERVER_PLACE) {
            @Override
            public boolean contains(Location location) {
                return location.getWorld() == world;
            }
        };
    }

    public static CoreWorld getWorld(World world) {
        return WorldManager.getManager().getCoreWorld(world);
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
}
