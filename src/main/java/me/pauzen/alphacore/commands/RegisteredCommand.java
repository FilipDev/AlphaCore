/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.commands.premade.*;
import me.pauzen.alphacore.utils.misc.Todo;

import java.util.HashMap;
import java.util.Map;

public enum RegisteredCommand {

    @Todo("Make Effects command")
    CRASH(new CrashCommand()),
    GOD(new GodCommand()),
    HGOD(new HungerGodCommand()),
    HEAL(new HealCommand()),
    FEED(new FeedCommand()),
    CLEAR_SCREEN(new ClearScreenCommand()),
    ABILITIES(new ActiveCommand()),;

    private static Map<String, Command> commandMap;

    public static Command getCommand(String name) {
        return commandMap.get(name);
    }

    public static void registerCommand(Command command) {
        if (commandMap == null) {
            commandMap = new HashMap<>();
        }

        for (String name : command.getNames()) {
            commandMap.put(name.toUpperCase(), command);
        }
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
