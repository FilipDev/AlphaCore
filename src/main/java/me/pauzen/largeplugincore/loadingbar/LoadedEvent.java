/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.loadingbar;

import me.pauzen.largeplugincore.events.MyCallablePlayerContainerEvent;
import me.pauzen.largeplugincore.players.MyPlayer;

public class LoadedEvent extends MyCallablePlayerContainerEvent {

    public LoadedEvent(MyPlayer myPlayer) {
        super(myPlayer);
    }
}
