package me.pauzen.bukkitcommonpluginapi.commands;

import me.pauzen.bukkitcommonpluginapi.utils.Tuple;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandManager {

    public Command getCommand(String commandName) {
        return CommandEnum.getCommand(commandName.toUpperCase());
    }
    
    public void executeCommand(String commandName, CommandSender commandSender, String[] arguments) {
        Tuple<Map<String, String>, String[]> argModifierTuple = getModifiers(arguments);
        getCommand(commandName).execute(commandSender, argModifierTuple.getB(), argModifierTuple.getA());
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
    }
    
    public static CommandManager getCommandManager() {
        return commandManager;
    }
    
}
