/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.ChatColor;

public class ErrorMessage implements Message {

    public static ErrorMessage CONSOLESENDER = new ErrorMessage("Command can only be sent by a player.");
    public static ErrorMessage SAME_TEAM = new ErrorMessage("Player is on the same team as you.");
    public static ErrorMessage PERMISSIONS = new ErrorMessage("No permissions for %s.");
    public static ErrorMessage SQL = new ErrorMessage("Could not connect to SQL database.");
    public static ErrorMessage COMMAND_NOT_FOUND = new ErrorMessage("Command chain %s non-existent.");

    private String errorMessage;

    private String errorPrefix = String.format("%s%sError: %s", ChatColor.RED, ChatColor.BOLD, ChatColor.DARK_RED);

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(String prefix, String errorMessage) {
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
