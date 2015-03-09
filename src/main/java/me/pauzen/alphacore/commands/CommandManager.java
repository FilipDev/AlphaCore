/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.utils.misc.Tuple;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandManager implements Registrable {

    public Command getCommand(String commandName) {
        return RegisteredCommand.getCommand(commandName.toLowerCase());
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

    @Nullify
    private static CommandManager manager;

    public static void register() {
        manager = new CommandManager();
        new CommandRunner();
        RegisteredCommand.values();
    }

    public static CommandManager getManager() {
        return manager;
    }

    public void registerCommand(Command command) {
        RegisteredCommand.registerCommand(command);
    }

    public List<Command> getCommands() {
        ArrayList<Command> list = new ArrayList<>();
        list.addAll(RegisteredCommand.getCommands());
        return list;
    }
}
