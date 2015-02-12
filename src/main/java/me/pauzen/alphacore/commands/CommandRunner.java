/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;

public class CommandRunner extends ListenerImplementation {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        process(event.getMessage(), event.getPlayer());
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        process(event.getCommand(), event.getSender());
    }

    private void process(String message, CommandSender commandSender) {
        String[] parts = message.split(" ");
        String commandString = parts[0].substring(1);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        Command command = CommandManager.getManager().getCommand(commandString);
        if (command != null) {
            CommandManager.getManager().executeCommand(command, commandSender, args);
        }
    }


}
