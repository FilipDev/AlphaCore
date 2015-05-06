/*
 *  Created by Filip P. on 5/4/15 6:04 PM.
 */

package me.pauzen.alphacore.worlds;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorldManager extends ListenerImplementation implements ModuleManager<CoreWorld> {

    @Nullify
    private static WorldManager manager;
    private Map<World, CoreWorld> worldMap = new HashMap<>();

    public static WorldManager getManager() {
        return manager;
    }

    public static void register() {
        manager = new WorldManager();
    }

    @Override
    public String getName() {
        return "worlds";
    }

    @Override
    public Collection<CoreWorld> getModules() {
        return worldMap.values();
    }

    @Override
    public void registerModule(CoreWorld module) {
        worldMap.put(module.getWorld(), module);
    }

    @Override
    public void unregisterModule(CoreWorld module) {
        worldMap.remove(module.getWorld());
    }

    public CoreWorld getCoreWorld(World world) {
        worldMap.putIfAbsent(world, new CoreWorld(world));
        return worldMap.get(world);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        CorePlayer corePlayer = CorePlayer.get(event.getPlayer());

        corePlayer.setPlace(getCoreWorld(corePlayer.getPlayer().getWorld()).getWorldPlace());
    }
}
