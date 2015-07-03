/*
 *  Created by Filip P. on 2/10/15 7:09 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.server.CoreServer;
import me.pauzen.alphacore.utils.misc.Tuple;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.worlds.WorldManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class PlaceManager extends ListenerImplementation implements ModuleManager<Place> {

    @Nullify
    private static PlaceManager manager;

    private Map<String, Place> places = new HashMap<>();

    public static PlaceManager getManager() {
        return manager;
    }

    @Override
    public void onEnable() {
        PlaceAction.values();
    }

    public void onEvent(Event e, PlaceAction placeAction, Predicate<Tuple> shouldRun) {
        if (!(e instanceof PlayerMoveEvent) && !(e instanceof BlockFromToEvent) && e instanceof Cancellable) {
            if (placeAction == null) {
                return;
            }

            if (CoreServer.getCoreServer().isDisallowed(placeAction)) {
                ((Cancellable) e).setCancelled(true);
            }
            
            Place place = null;
            try {
                Player player = placeAction.getPlayerGetter().getPlayer(new EventContainer<>(e.getClass(), e));
                if (player != null) {
                    place = PlaceAction.getPlace(player);
                }
            } catch (ClassCastException ignored) {
            }

            if (place == null) {
                return;
            }
            
            if (shouldRun.test(new Tuple<>(e, place))) {
                if (!place.isAllowed(placeAction)) {
                    ((Cancellable) e).setCancelled(true);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "places";
    }

    @Override
    public Collection<Place> getModules() {
        return places.values();
    }

    @Override
    public void registerModule(Place module) {
        places.put(module.getName(), module);
    }

    @Override
    public void unregisterModule(Place module) {
        places.remove(module.getName());
    }

    public Place getPlace(CorePlayer corePlayer) {
        if (corePlayer.getCurrentPlace() == null) {
            corePlayer.setPlace(WorldManager.getManager().getCoreWorld(corePlayer.getPlayer().getWorld()).getWorldPlace());
        }
        return corePlayer.getCurrentPlace();
    }
}
