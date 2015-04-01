/*
 *  Created by Filip P. on 3/12/15 12:03 AM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.inventory.misc.ClickType;

public interface Interactable<T> {

    public void onInteract(T value, ClickType clickType);

}
