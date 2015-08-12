/*
 *  Created by Filip P. on 2/10/15 7:09 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.places.actions.PhysicalAction;
import me.pauzen.alphacore.places.actions.PlaceAction;
import me.pauzen.alphacore.places.misc.EventContainer;
import me.pauzen.alphacore.places.places.PhysicalPlace;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.server.CoreServer;
import me.pauzen.alphacore.utils.misc.Tuple;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.worlds.WorldManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;
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

    public void onPlayerEvent(Event event, PlaceAction placeAction, Predicate<Tuple> shouldRun) {
        if (!(event instanceof PlayerMoveEvent) && !(event instanceof BlockFromToEvent) && event instanceof Cancellable) {
            if (placeAction == null) {
                return;
            }

            if (CoreServer.getCoreServer().isDisallowed(placeAction)) {
                ((Cancellable) event).setCancelled(true);
            }

            Place place = null;
            try {
                Player player = placeAction.getPlayerGetter().getPlayer(new EventContainer<>(event.getClass(), event));
                if (player != null) {
                    place = CorePlayer.get(player).getCurrentPlace();
                }
            } catch (ClassCastException ignored) {
            }

            if (place == null) {
                return;
            }

            if (shouldRun.test(new Tuple<>(event, place))) {
                if (!place.isAllowed(placeAction)) {
                    ((Cancellable) event).setCancelled(true);
                }
            }
        }
    }

    public void onPhysicalEvent(Event event, PhysicalAction physicalAction) {
        List<PhysicalPlace> bottomPlaces = getBottomPlaces(Place.getServerPlace());

        Location location = physicalAction.getLocationGetter().apply(event);

        boolean allow = true;

        for (PhysicalPlace bottomPlace : bottomPlaces) {
            if (bottomPlace.contains(location)) {
                if (!bottomPlace.isAllowed(physicalAction)) {
                    allow = false;
                }
            }
        }

        if (!allow) {
            ((Cancellable) event).setCancelled(true);
        }

    }

    public List<PhysicalPlace> getBottomPlaces(Place place) {
        List<PhysicalPlace> bottom = new ArrayList<>();

        for (Place subPlace : place.getSubPlaces()) {
            if (subPlace instanceof PhysicalPlace) {

                if (subPlace.hasSubPlaces()) {
                    bottom.addAll(getBottomPlaces(subPlace));
                }
                else {
                    bottom.add((PhysicalPlace) subPlace);
                }
            }
        }

        return bottom;
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
