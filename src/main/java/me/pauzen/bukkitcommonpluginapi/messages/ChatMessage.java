package me.pauzen.bukkitcommonpluginapi.messages;

import org.bukkit.ChatColor;

public enum ChatMessage implements Message {

    TOGGLED("Ability %s has been %s."),
    SET("Ability %s has been set to %s."),
    ;
    
    private String message;

    private ChatMessage(String message) {
        this.message = message;
    }

    @Override
    public String getPrefix() {
        return ChatColor.GRAY + "Server: " + ChatColor.WHITE;
    }

    @Override
    public String getRawMessage() {
        return this.message;
    }
}