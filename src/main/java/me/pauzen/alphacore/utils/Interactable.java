/*
 *  Created by Filip P. on 3/12/15 12:03 AM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.inventory.misc.ClickType;

public interface Interactable<T> {

    /**
     * Called when T value is interacted with.
     *
     * @param value     Object interacted with.
     * @param clickType Type of click used to interact with object.
     */
    public void onInteract(T value, ClickType clickType);

}
