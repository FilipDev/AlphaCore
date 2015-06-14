/*
 *  Created by Filip P. on 6/3/15 10:23 PM.
 */

package me.pauzen.alphacore.utils.executors;

import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.players.CorePlayer;

public interface ClickExecutor<T, E> {

    public void onClick(ClickType clickType, CorePlayer clicker, T t, E e);

}
