package me.pauzen.bukkitcommonpluginapi.commands;

import me.pauzen.bukkitcommonpluginapi.Core;
import me.pauzen.bukkitcommonpluginapi.utils.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandManager {

    public Command getCommand(String commandName) {
        return RegisteredCommand.getCommand(commandName.toUpperCase());
    }
    
    public void executeCommand(Command command, CommandSender commandSender, String[] arguments) {
        Tuple<Map<String, String>, String[]> argModifierTuple = getModifiers(arguments);
        command.execute(commandSender, argModifierTuple.getB(), argModifierTuple.getA());
    }
    
    private Tuple<Map<String, String>, String[]> getModifiers(String[] args) {
        Map<String, String> modifiers = new HashMap<>();
        List<String> newArgs =  new ArrayList<>();
        Collections.addAll(newArgs, args);
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                String key = arg.substring(1);
                String value = args[i + 1];
                modifiers.put(key, value);
                newArgs.remove(arg);
                newArgs.remove(value);
            }
        }
        
        return new Tuple<>(newArgs.toArray(new String[newArgs.size()]), modifiers);
    }

    private static CommandManager commandManager;

    public static void registerManager() {
        commandManager = new CommandManager();
        Bukkit.getPluginManager().registerEvents(new CommandRunner(), Core.getCore());
    }
    
    public static CommandManager getCommandManager() {
        return commandManager;
    }
    
    public void registerCommand(Command command) {
        RegisteredCommand.registerCommand(command);
    }
    
}
