/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.commands.childcommands.CrashCommand;
import me.pauzen.alphacore.commands.childcommands.GodCommand;

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
