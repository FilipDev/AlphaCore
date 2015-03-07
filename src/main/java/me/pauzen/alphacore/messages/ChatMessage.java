/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import org.bukkit.ChatColor;

public enum ChatMessage implements Message {

    TOGGLED("Ability %s has been %s."),
    SET("Ability %s has been set to %s."),
    JOINED_TEAM("You have been added to the team %s"),
    FED(ChatColor.GOLD + "Your apetite has been sated."),
    HEALED(ChatColor.RED + "You have been fully healed."),
    LINE_SPACER(ChatColor.DARK_GRAY + "------------------------"),
    SPACER(ChatColor.GRAY + "=====" + " %s " + ChatColor.GRAY + "====="),
    LIST_ELEMENT(ChatColor.GRAY + "%s: " + "%s"),;

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