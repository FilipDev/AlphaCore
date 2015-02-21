/*
 *  Created by Filip P. on 2/13/15 8:01 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.events.CallableEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class MessageSendEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private CommandSender commandSender;

    public MessageSendEvent(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }
}
