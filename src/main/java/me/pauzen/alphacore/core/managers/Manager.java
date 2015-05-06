/*
 *  Created by Filip P. on 5/6/15 3:26 AM.
 */

package me.pauzen.alphacore.core.managers;

import me.pauzen.alphacore.utils.reflection.Nullifiable;

public interface Manager extends Nullifiable {

    public String getName();
    
    public default void onEnable() {}
    
    public default void onDisable() {}

}
