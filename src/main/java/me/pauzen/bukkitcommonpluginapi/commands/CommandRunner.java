package me.pauzen.bukkitcommonpluginapi.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;

public class CommandRunner implements Listener {

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
        Command command = CommandManager.getCommandManager().getCommand(commandString);
        if (command != null) {
            CommandManager.getCommandManager().executeCommand(command, commandSender, args);
        }
    }
    

}
