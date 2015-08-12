/*
 *  Created by Filip P. on 2/11/15 11:37 PM.
 */

package me.pauzen.alphacore.places.actions;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.commands.events.CommandRunEvent;
import me.pauzen.alphacore.places.*;
import me.pauzen.alphacore.places.misc.PlayerGetter;
import me.pauzen.alphacore.places.places.CorePlace;
import me.pauzen.alphacore.places.places.PhysicalPlace;
import me.pauzen.alphacore.players.CorePlayer;
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

public enum PlaceAction {

    //COMBAT
    PVP(AttackEvent.class, e -> e.event().getAttacker()),
    PVE(EntityDamageByEntityEvent.class, e -> (Player) e.event().getDamager()),

    //BLOCK MANIPULATION
    BLOCK_PLACE(BlockPlaceEvent.class, e -> e.event().getPlayer()),
    BLOCK_BREAK(BlockBreakEvent.class, e -> e.event().getPlayer()),

    //TELEPORTATION
    TELEPORT_IN(PlayerTeleportEvent.class, e -> e.event().getPlayer(), t -> {
        CorePlace place = t.getSecond();
        if (place instanceof PhysicalPlace) {
            PhysicalPlace physicalPlace = (PhysicalPlace) place;
            return physicalPlace.contains(t.getFirst().getTo());
        }
        return false;
    }),
    TELEPORT_OUT(PlayerTeleportEvent.class, e -> e.event().getPlayer(), t -> {
        CorePlace place = t.getSecond();
        if (place instanceof PhysicalPlace) {
            PhysicalPlace physicalPlace = (PhysicalPlace) place;
            return physicalPlace.contains(t.getFirst().getFrom());
        }
        return false;
    }),

    //CHAT
    CHAT(AsyncPlayerChatEvent.class),
    COMMAND(CommandRunEvent.class, e -> e.event().getPlayer().getPlayer()),

    DROP_ITEM(PlayerDropItemEvent.class),
    PICKUP_ITEM(PlayerPickupItemEvent.class),

    FILL_BUCKET(PlayerBucketFillEvent.class),
    EMPTY_BUCKET(PlayerBucketEmptyEvent.class),

    TARGETABLE(EntityTargetLivingEntityEvent.class, e -> {
        LivingEntity target = e.event().getTarget();
        return (target instanceof Player) ? (Player) target : null;
    });

    private Class        eventClass;
    private PlayerGetter playerGetter;

    <E extends Event> PlaceAction(Class<E> eventClass, PlayerGetter<E> playerGetter, Predicate<Tuple<E, CorePlace>> shouldRun) {
        this.eventClass = eventClass;
        this.playerGetter = playerGetter;
        
        Bukkit.getPluginManager().registerEvent(eventClass, PlaceManager.getManager(), EventPriority.HIGHEST, (listener, event) -> {
            Predicate predicate = shouldRun;
            PlaceManager.getManager().onPlayerEvent(event, this, predicate);
        }, Core.getCore());
    }

    <E extends Event> PlaceAction(Class<E> eventClass, PlayerGetter<E> playerGetter) {
        this(eventClass, playerGetter, e -> true);
    }

    <E extends PlayerEvent> PlaceAction(Class<E> eventClass) {
        this(eventClass, e -> e.event().getPlayer(), e -> true);
    }

    public Class getEventClass() {
        return eventClass;
    }

    public PlayerGetter getPlayerGetter() {
        return playerGetter;
    }
}