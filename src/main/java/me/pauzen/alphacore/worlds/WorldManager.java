/*
 *  Created by Filip P. on 5/4/15 6:04 PM.
 */

package me.pauzen.alphacore.worlds;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();
        CoreWorld world = CoreWorld.get(player.getWorld());

        if (world.isApplied(WorldProperty.PREVENT_JOINING)) {
            player.teleport(event.getFrom().getSpawnLocation());
        }

        CorePlayer corePlayer = CorePlayer.get(player);

        corePlayer.setPlace(getCoreWorld(corePlayer.getPlayer().getWorld()).getWorldPlace());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent event) {

        CoreWorld world = CoreWorld.get(event.getWorld());

        if (world.isApplied(WorldProperty.LOCK_WEATHER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {

        if (event.getUpdateType() == UpdateType.TICK) {
            for (World world : Bukkit.getWorlds()) {

                CoreWorld coreWorld = CoreWorld.get(world);

                if (coreWorld.isApplied(WorldProperty.LOCK_TIME)) {
                    coreWorld.getWorld().setTime(coreWorld.getLockTime());
                }
            }
        }
    }
}
