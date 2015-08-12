/*
 *  Created by Filip P. on 7/5/15 3:54 PM.
 */

package me.pauzen.alphacore.places.actions;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.places.PlaceManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.function.Function;

public enum PhysicalAction {

    PHYSICS(BlockPhysicsEvent.class, e -> e.getBlock().getLocation()),
    ENTITY_SPAWN(EntitySpawnEvent.class, EntitySpawnEvent::getLocation),
    ;

    private final Class    event;
    private final Function locationGetter;

    <E extends Event> PhysicalAction(Class<E> event, Function<E, Location> locationGetter) {
        this.event = event;
        this.locationGetter = locationGetter;

        Bukkit.getPluginManager().registerEvent(event, PlaceManager.getManager(), EventPriority.HIGHEST, (listener, e) -> {
            PlaceManager.getManager().onPhysicalEvent(e, this);
        }, Core.getCore());
    }

    public Class getEvent() {
        return event;
    }

    public <E extends Event> Function<E, Location> getLocationGetter() {
        return locationGetter;
    }
}
