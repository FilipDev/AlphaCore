/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.utils.misc.Tuple;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandManager implements ModuleManager<Command> {

    @Nullify
    private static CommandManager manager;
    private        CommandRunner  commandRunner;

    public static CommandManager getManager() {
        return manager;
    }

    @Override
    public void onEnable() {
        commandRunner = new CommandRunner();
        RegisteredCommand.values();
    }

    @Override
    public void onDisable() {
        commandRunner.unload();
    }

    public Command getCommand(String commandName) {
        String[] names = commandName.split(" ");
        Command command = RegisteredCommand.getCommand(names[0].toLowerCase());
        if (names.length > 1) {
            for (int i = 1; i < names.length; i++) {
                String name = names[i];
                command = command.getSubCommands().get(name);
            }
        }

        return command;
    }

    public void executeCommand(Command command, CommandSender commandSender, String[] arguments) {
        Tuple<Map<String, String>, String[]> argModifierTuple = getModifiers(arguments);
        command.execute(commandSender, argModifierTuple.getSecond(), argModifierTuple.getFirst());
    }

    private Tuple<Map<String, String>, String[]> getModifiers(String[] args) {
        Map<String, String> modifiers = new HashMap<>();
        List<String> newArgs = new ArrayList<>();
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

        return new Tuple<>(modifiers, newArgs.toArray(new String[newArgs.size()]));
    }

    public void registerCommand(Command command) {
        RegisteredCommand.registerCommand(command, command.getOwner());
    }

    public Map<String, Command> getCommands() {
        return RegisteredCommand.getCommands();
    }

    @Override
    public String getName() {
        return "commands";
    }

    @Override
    public Collection<Command> getModules() {
        return getCommands().values();
    }

    @Override
    public void registerModule(Command module) {
        registerCommand(module);
    }

    @Override
    public void unregisterModule(Command module) {
        RegisteredCommand.unregisterCommand(module);
    }
}
