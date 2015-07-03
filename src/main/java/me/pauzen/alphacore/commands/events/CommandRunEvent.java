/*
 *  Created by Filip P. on 7/2/15 5:38 PM.
 */

package me.pauzen.alphacore.commands.events;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.events.CallablePlayerEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.HandlerList;

public class CommandRunEvent extends CallablePlayerEvent {
    
    private final Command command;

    public CommandRunEvent(CorePlayer corePlayer, Command command) {
        super(corePlayer);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
