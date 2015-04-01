/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.commands.alphacore.AlphaCoreCommand;

import java.util.HashMap;
import java.util.Map;

public enum RegisteredCommand {

    ALPHA_CORE(new AlphaCoreCommand()),
    ACTIVE(new ActiveCommand());

    private static Map<String, Command> commandMap;
    private Command command;

    RegisteredCommand(Command command) {
        this.command = command;
        registerCommand(this.getCommand());
    }

    public static Command getCommand(String name) {
        return commandMap.get(name);
    }

    public static void registerCommand(Command command) {
        if (commandMap == null) {
            commandMap = new HashMap<>();
        }

        for (String name : command.getNames()) {
            commandMap.put(name.toLowerCase(), command);
        }
    }

    public static Map<String, Command> getCommands() {
        return commandMap;
    }

    public Command getCommand() {
        return command;
    }


}
