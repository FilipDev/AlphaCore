/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.doublejump;

import me.pauzen.largeplugincore.events.MyCallablePlayerContainerEvent;
import me.pauzen.largeplugincore.players.MyPlayer;

public class DoubleJumpEvent extends MyCallablePlayerContainerEvent {
    
    public DoubleJumpEvent(MyPlayer myPlayer) {
        super(myPlayer);
    }
}
