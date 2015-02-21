/*
 *  Created by Filip P. on 2/12/15 12:42 AM.
 */

package me.pauzen.alphacore.places;

import org.bukkit.entity.Player;

public interface PlayerGetter<E> {

    public Player getPlayer(EventContainer<E> eventContainer);

}
