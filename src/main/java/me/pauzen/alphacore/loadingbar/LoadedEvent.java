/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.loadingbar;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;

public class LoadedEvent extends CallablePlayerContainerEvent {

    public LoadedEvent(CorePlayer CorePlayer) {
        super(CorePlayer);
    }
}
