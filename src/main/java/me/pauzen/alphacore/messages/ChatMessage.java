/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import org.bukkit.ChatColor;

public class ChatMessage implements Message {

    public static ChatMessage TOGGLED = new ChatMessage("Ability %s has been %s.");
    public static ChatMessage SET = new ChatMessage("Ability %s has been set to %s.");
    public static ChatMessage JOINED_TEAM = new ChatMessage("You have been added to the team %s");
    public static ChatMessage LINE_SPACER = new ChatMessage(ChatColor.DARK_GRAY + "------------------------");
    public static ChatMessage SPACER = new ChatMessage(ChatColor.GRAY + "=====" + " %s " + ChatColor.GRAY + "=====");
    public static ChatMessage LIST_ELEMENT = new ChatMessage(ChatColor.GRAY + "%s: " + "%s");
    public static ChatMessage ABOUT = new ChatMessage("AlphaCore by Filip.");

    private String message;

    public ChatMessage(String message) {
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