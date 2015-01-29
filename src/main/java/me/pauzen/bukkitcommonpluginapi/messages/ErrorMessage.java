package me.pauzen.bukkitcommonpluginapi.messages;

import org.bukkit.ChatColor;

public enum ErrorMessage implements Message {
    
    CONSOLESENDER("Command can only be sent by a player."),
    ;

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
