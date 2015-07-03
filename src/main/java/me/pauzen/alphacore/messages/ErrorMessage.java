/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.messages.json.Action;
import me.pauzen.alphacore.messages.json.MessagePart;
import me.pauzen.alphacore.messages.json.events.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ErrorMessage implements Message {

    public static final ErrorMessage INVALID_ARGUMENTS = new ErrorMessage(new MessagePart("Invalid arguments used.") {
        {
            addEvent(new HoverEvent(Action.SHOW_TEXT, "%s"));
            setColor(ChatColor.DARK_RED);
        }
    });
    public static final ErrorMessage CONSOLESENDER     = new ErrorMessage("Command can only be sent by a player.");
    public static final ErrorMessage PERMISSIONS       = new ErrorMessage("Not permitted to use %s.");
    public static final ErrorMessage SQL               = new ErrorMessage("Could not connect to SQL database.");
    public static final ErrorMessage COMMAND_NOT_FOUND = new ErrorMessage("Command chain %s non-existent.");
    public static final ErrorMessage COOLDOWN          = new ErrorMessage("%s is still on cooldown." + ChatColor.RED + " (%s remaining)");

    private String      errorMessage;
    private MessagePart json;

    private String errorPrefix = String.format("%s%sError: %s", ChatColor.RED, ChatColor.BOLD, ChatColor.DARK_RED);

    public ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(MessagePart json) {
        setJson(json);
    }

    public ErrorMessage(String prefix, String errorMessage) {
        this(errorMessage);
        this.errorPrefix = prefix;
    }

    public void setJson(MessagePart json) {
        this.json = json;
        this.json.setMessage(getPrefix() + json.getMessage() + ChatColor.RED + ChatColor.BOLD + " [?]");
        this.errorMessage = json.getMessage();
    }

    @Override
    public void send(CommandSender commandSender, String... placeHolders) {
        if (commandSender instanceof Player && json != null) {
            new JSONMessageBuilder().add(json).send((Player) commandSender, placeHolders);
        }
        else {
            Message.super.send(commandSender, placeHolders);
        }
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
