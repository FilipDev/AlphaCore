/*
 *  Created by Filip P. on 2/11/15 11:37 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Todo("Add more values.")
public enum PlaceAction {

    //COMBAT
    PVP(AttackEvent.class, e -> e.event().getAttacker()),
    PVE(EntityDamageByEntityEvent.class, e -> (Player) e.event().getDamager()),

    //BLOCK MANIPULATION
    BLOCK_PLACE(BlockPlaceEvent.class, e -> e.event().getPlayer()),
    BLOCK_BREAK(BlockBreakEvent.class, e -> e.event().getPlayer()),

    //CHAT
    CHAT(AsyncPlayerChatEvent.class, e -> e.event().getPlayer()),;

    private Class eventClass;
    private PlayerGetter playerGetter;

    <E> PlaceAction(Class<E> eventClass, PlayerGetter<E> playerGetter) {
        this.eventClass = eventClass;
        this.playerGetter = playerGetter;
        Bukkit.getPluginManager().registerEvent((Class<? extends Event>) eventClass, PlaceManager.getManager(), EventPriority.HIGHEST, (listener, event) -> {
            PlaceManager.getManager().onEvent(event);
        }, Core.getCore());
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