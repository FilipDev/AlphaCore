/*
 *  Created by Filip P. on 4/1/15 6:17 PM.
 */

package me.pauzen.alphacore.applyable;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;

public interface Applyable {
    
    void apply(CorePlayer corePlayer);
    
    default void apply(Player player) {
        apply(CorePlayer.get(player));
    }
    
    void remove(CorePlayer corePlayer);
    
    default void remove(Player player) {
        remove(CorePlayer.get(player));
    }
    
    boolean isInvisible();
    
    String getName();
    
    boolean hasActivated(CorePlayer corePlayer);
    
    default boolean hasActivated(Player player) {
        return hasActivated(CorePlayer.get(player));
    }

}
