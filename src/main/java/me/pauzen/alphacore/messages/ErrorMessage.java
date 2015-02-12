/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import org.bukkit.ChatColor;

public enum ErrorMessage implements Message {

    CONSOLESENDER("Command can only be sent by a player."),
    SAME_TEAM("Player is on the same team as you."),;

    private String errorMessage;

    private String errorPrefix = String.format("%s%sError: %s", ChatColor.RED, ChatColor.BOLD, ChatColor.DARK_RED);

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ErrorMessage(String prefix, String errorMessage) {
        this(errorMessage);
        this.errorPrefix = prefix;
    }

    @Override
    public String getPrefix() {
        return errorPrefix;
    }

    @Override
    public String getRawMessage() {
        return this.errorMessage;
    }
}
