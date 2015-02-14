/*
 *  Created by Filip P. on 2/10/15 7:09 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlaceManager extends ListenerImplementation implements Registrable {

    @Nullify
    private static Place DEFAULT_PLACE;
    
    public void onEvent(Event e) {
        if (!(e instanceof PlayerMoveEvent) && !(e instanceof BlockFromToEvent) && e instanceof Cancellable) {
            PlaceAction placeAction = PlaceAction.getPlaceAction(e.getClass());

            if (placeAction == null) {
                return;
            }

            Place place = null;
            try {
                place = placeAction.getPlaceGetter().getPlace(new EventContainer<>(e.getClass(), e));
            } catch (ClassCastException ignored) {
            }

            if (place == null) {
                return;
            }

            
            
            if (!place.isAllowed(placeAction)) {
                ((Cancellable) e).setCancelled(true);
            }
        }
    }
    
    @Nullify
    private static PlaceManager manager;

    public static PlaceManager getManager() {
        return manager;
    }

    public static void register() {
        manager = new PlaceManager();
        DEFAULT_PLACE = new Place("DEFAULT") {
            @Override
            public boolean contains(Location location) {
                return true;
            }
        };
        DEFAULT_PLACE.getPlaceActionChecker().disallow(PlaceAction.CHAT);
    }

    public static Place getDefaultPlace() {
        return DEFAULT_PLACE;
    }
}
