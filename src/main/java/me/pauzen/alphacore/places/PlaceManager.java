/*
 *  Created by Filip P. on 2/10/15 7:09 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Tuple;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.worlds.WorldManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class PlaceManager extends ListenerImplementation implements ModuleManager<Place> {

    @Nullify
    private static PlaceManager manager;

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
        return Collections.emptyList();
    }

    @Override
    public void registerModule(Place module) {
    }

    @Override
    public void unregisterModule(Place module) {
    }

    public Place getPlace(CorePlayer corePlayer) {
        if (corePlayer.getCurrentPlace() == null) {
            corePlayer.setPlace(WorldManager.getManager().getCoreWorld(corePlayer.getPlayer().getWorld()).getWorldPlace());
        }
        return corePlayer.getCurrentPlace();
    }
}
