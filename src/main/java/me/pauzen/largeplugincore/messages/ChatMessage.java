/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.messages;

import org.bukkit.ChatColor;

public enum ChatMessage implements Message {

    TOGGLED("Ability %s has been %s."),
    SET("Ability %s has been set to %s."),
    JOINED_TEAM("You have been added to the team %s"),
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