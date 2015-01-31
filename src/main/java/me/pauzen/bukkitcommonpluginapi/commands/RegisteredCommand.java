package me.pauzen.bukkitcommonpluginapi.commands;

import me.pauzen.bukkitcommonpluginapi.commands.childcommands.CrashCommand;
import me.pauzen.bukkitcommonpluginapi.commands.childcommands.GodCommand;

import java.util.HashMap;
import java.util.Map;

public enum RegisteredCommand {

    CRASH(new CrashCommand()),
    GOD(new GodCommand()),
    ;

    private static Map<String, Command> commandMap = new HashMap<>();

    public static Command getCommand(String name) {
        return commandMap.get(name);
    }

    public static void registerCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    private Command command;

    RegisteredCommand(Command command) {
        this.command = command;
        registerCommand(this.getCommand());
    }
    
    public Command getCommand() {
        return command;
    }


}
