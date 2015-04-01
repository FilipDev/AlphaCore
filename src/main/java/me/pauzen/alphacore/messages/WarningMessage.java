/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import org.bukkit.ChatColor;

public enum WarningMessage implements Message {

    EXAMPLE("Example warning message."),;

    String warningMessage;

    WarningMessage(String warningMessage) {
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
