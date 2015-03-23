/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package doublejump;

import me.pauzen.alphacore.events.CallablePlayerContainerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class DoubleJumpEvent extends CallablePlayerContainerEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DoubleJumpEvent(CorePlayer CorePlayer) {
        super(CorePlayer);
    }
}
