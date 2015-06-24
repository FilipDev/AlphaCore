/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import org.bukkit.ChatColor;

public class WarningMessage implements Message {

    public static final WarningMessage BORDER_BROSSED = new WarningMessage("Cannot cross world border.");

    private String warningMessage;

    public WarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    @Override
    public String getPrefix() {
        return ChatColor.YELLOW + "Warning: " + ChatColor.GOLD;
    }

    @Override
    public String getRawMessage() {
        return warningMessage;
    }
}
