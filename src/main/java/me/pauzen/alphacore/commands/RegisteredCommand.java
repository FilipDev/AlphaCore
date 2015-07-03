/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.commands.premade.ActiveCommand;
import me.pauzen.alphacore.commands.premade.TrackersCommand;
import me.pauzen.alphacore.commands.premade.alphacore.AlphaCoreCommand;
import me.pauzen.alphacore.commands.premade.alphacore.Help;
import me.pauzen.alphacore.utils.reflection.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum RegisteredCommand {

    ALPHA_CORE(new AlphaCoreCommand()),
    ACTIVE(new ActiveCommand()),
    TRACKERS(new TrackersCommand());

    private static Map<String, Command> commandMap;
    private        Command              command;

    RegisteredCommand(Command command) {
        this.command = command;
        registerCommand(this.getCommand(), Core.getCore());
    }

    public static Command getCommand(String name) {
        return commandMap.get(name);
    }

    public static void registerCommand(Command command, Plugin plugin) {

        command.onRegister();

        if (commandMap == null) {
            commandMap = new HashMap<>();
        }

        for (String name : command.getNames()) {
            commandMap.put(name.toLowerCase(), command);
        }

        Reflection<Server> serverReflection = new Reflection<>(Bukkit.getServer());

        SimpleCommandMap commandMap = (SimpleCommandMap) serverReflection.getValue("commandMap");

        for (String name : command.getNames()) {
            try {
                Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);
                PluginCommand pluginCommand = constructor.newInstance(name, plugin);

                pluginCommand.setDescription(command.getDescription().replace("%default%", Help.DEFAULT_DESCRIPTION));

                pluginCommand.setTabCompleter((commandSender, c, s, strings) -> {
                    ArrayList<String> completions = new ArrayList<>();
                    String lastToken = strings[strings.length - 1];

                    String combined = combine(s, strings);
                    combined = combined.substring(0, combined.lastIndexOf(" "));

                    Command command1 = CommandManager.getManager().getCommand(combined);

                    command1.getSubCommands()
                            .keySet()
                            .stream()
                            .filter(subCommandName -> subCommandName.toLowerCase().startsWith(lastToken.toLowerCase()))
                            .forEach(completions::add);

                    if (command1.getSuggestPlayers()) {
                        Bukkit.getOnlinePlayers()
                              .stream()
                              .filter(player -> player.getName().toLowerCase().startsWith(lastToken.toLowerCase()))
                              .forEach(player -> completions.add(player.getName()));
                    }
                    return completions;
                });

                commandMap.register(name, pluginCommand);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, Command> getCommands() {
        return commandMap;
    }

    private static String combine(String string, String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(string);

        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(" ");
            stringBuilder.append(strings[i]);
        }

        return stringBuilder.toString();
    }

    public static void unregisterCommand(Command command) {
        command.getNames().forEach(commandMap::remove);
        Reflection<Server> serverReflection = new Reflection<>(Bukkit.getServer());

        SimpleCommandMap commandMap = (SimpleCommandMap) serverReflection.getValue("commandMap");

        Reflection<SimpleCommandMap> simpleCommandMapReflection = new Reflection<>(commandMap);
        Map<String, Command> knownCommands = (Map<String, Command>) simpleCommandMapReflection.getValue("knownCommands");
        for (String name : command.getNames()) {
            knownCommands.remove(name);
        }
    }

    public Command getCommand() {
        return command;
    }
}
