/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.doublejump;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;

public class DoubleJumpEvent extends CallablePlayerContainerEvent {
    
    public DoubleJumpEvent(CorePlayer CorePlayer) {
        super(CorePlayer);
    }
}
