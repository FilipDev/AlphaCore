/*
 *  Created by Filip P. on 2/11/15 11:37 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@Todo("Add more values.")
public enum PlaceAction {

    //COMBAT
    PVP(AttackEvent.class, e -> CorePlayer.get(e.getEvent().getAttacker()).getCurrentPlace()),
    PVE(EntityDamageByEntityEvent.class, e -> CorePlayer.get((Player) e.getEvent().getDamager()).getCurrentPlace()),
    
    //BLOCK MANIPULATION
    BLOCK_PLACE(BlockPlaceEvent.class), 
    BLOCK_BREAK(BlockBreakEvent.class),;

    public static PlaceAction getPlaceAction(Class<? extends Event> eventClass) {
        for (PlaceAction placeAction : PlaceAction.values()) {
            if (placeAction.getEventClass().equals(eventClass)) {
                return placeAction;
            }
        }
        return null;
    }

    private Class eventClass;

    public Class getEventClass() {
        return eventClass;
    }

    private PlaceGetter placeGetter;

    public PlaceGetter getPlaceGetter() {
        return placeGetter;
    }
    
    <E> PlaceAction(Class<E> eventClass, PlaceGetter<E> placeGetter) {
        this.eventClass = eventClass;
        this.placeGetter = placeGetter;
    }
}