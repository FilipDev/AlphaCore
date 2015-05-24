/*
 *  Created by Filip P. on 2/11/15 11:37 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import me.pauzen.alphacore.utils.misc.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.*;

import java.util.function.Predicate;

@Todo("Add more values.")
public enum PlaceAction {

    //COMBAT
    PVP(AttackEvent.class, e -> e.event().getAttacker()),
    PVE(EntityDamageByEntityEvent.class, e -> (Player) e.event().getDamager()),

    //BLOCK MANIPULATION
    BLOCK_PLACE(BlockPlaceEvent.class, e -> e.event().getPlayer()),
    BLOCK_BREAK(BlockBreakEvent.class, e -> e.event().getPlayer()),

    //TELEPORTATION
    TELEPORT_IN(PlayerTeleportEvent.class, e -> e.event().getPlayer(), t -> {
        Place place = t.getSecond();
        if (place instanceof PhysicalPlace) {
            PhysicalPlace physicalPlace = (PhysicalPlace) place;
            return physicalPlace.contains(t.getFirst().getTo());
        }
        return false;
    }),
    TELEPORT_OUT(PlayerTeleportEvent.class, e -> e.event().getPlayer(), t -> {
        Place place = t.getSecond();
        if (place instanceof PhysicalPlace) {
            PhysicalPlace physicalPlace = (PhysicalPlace) place;
            return physicalPlace.contains(t.getFirst().getFrom());
        }
        return false;
    }),

    //CHAT
    CHAT(AsyncPlayerChatEvent.class),

    DROP_ITEM(PlayerDropItemEvent.class),
    
    PICKUP_ITEM(PlayerPickupItemEvent.class),

    USE_BUCKET(PlayerBucketEvent.class),

    TARGETABLE(EntityTargetLivingEntityEvent.class, e -> {
        LivingEntity target = e.event().getTarget();
        return (target instanceof Player) ? (Player) target : null;
    });

    private Class        eventClass;
    private PlayerGetter playerGetter;

    <E extends Event> PlaceAction(Class<E> eventClass, PlayerGetter<E> playerGetter, Predicate<Tuple<E, Place>> shouldRun) {
        this.eventClass = eventClass;
        this.playerGetter = playerGetter;
        Bukkit.getPluginManager().registerEvent(eventClass, PlaceManager.getManager(), EventPriority.HIGHEST, (listener, event) -> {
            Predicate predicate = shouldRun;
            PlaceManager.getManager().onEvent(event, this, predicate);
        }, Core.getCore());
    }

    <E extends Event> PlaceAction(Class<E> eventClass, PlayerGetter<E> playerGetter) {
        this(eventClass, playerGetter, e -> true);
    }

    <E extends PlayerEvent> PlaceAction(Class<E> eventClass) {
        this(eventClass, e -> e.event().getPlayer(), e -> true);
    }

    public static PlaceAction getPlaceAction(Class<? extends Event> eventClass) {
        for (PlaceAction placeAction : PlaceAction.values()) {
            if (placeAction.getEventClass().equals(eventClass)) {
                return placeAction;
            }
        }
        return null;
    }

    public static Place getPlace(Player player) {
        return CorePlayer.get(player).getCurrentPlace();
    }

    public Class getEventClass() {
        return eventClass;
    }

    public PlayerGetter getPlayerGetter() {
        return playerGetter;
    }
}