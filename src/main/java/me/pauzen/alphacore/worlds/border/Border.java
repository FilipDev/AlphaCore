/*
 *  Created by Filip P. on 6/22/15 2:18 AM.
 */

package me.pauzen.alphacore.worlds.border;

import me.pauzen.alphacore.messages.WarningMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Border {

    private final int      size;
    private final Location centre;
    
    private WarningMessage borderCrossMessage = WarningMessage.BORDER_BROSSED;

    public Border(int size, Location centre) {
        this.size = size;
        this.centre = centre;
    }

    public int getSize() {
        return size;
    }

    public Location getCentre() {
        return centre;
    }

    public abstract boolean isWithin(Player player);
    
    public abstract void revert(Player player);

    public WarningMessage getBorderCrossMessage() {
        return borderCrossMessage;
    }

    public void setBorderCrossMessage(WarningMessage borderCrossMessage) {
        this.borderCrossMessage = borderCrossMessage;
    }
}
