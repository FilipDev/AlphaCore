/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.events;

import me.pauzen.largeplugincore.players.MyPlayer;

public class MyCallablePlayerContainerEvent extends MyCallableEvent {
    
    private MyPlayer myPlayer;

    public MyCallablePlayerContainerEvent(MyPlayer myPlayer) {
        this.myPlayer = myPlayer;
    }

    public MyPlayer getPlayer() {
        return myPlayer;
    }
}
