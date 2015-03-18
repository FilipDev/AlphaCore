/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.commands.premade.*;
import me.pauzen.alphacore.commands.premade.alphacore.AlphaCoreCommand;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum RegisteredCommand {

    CRASH(new CrashCommand()),
    GOD(new GodCommand()),
    HGOD(new HungerGodCommand()),
    HEAL(new HealCommand()),
    COLORS(new ColorsCommand()),
    FEED(new FeedCommand()),
    CLEAR_SCREEN(new ClearScreenCommand()),
    ALPHA_CORE(new AlphaCoreCommand()),
    KILL_ALL(new KillAllCommand()),
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
            commandMap.put(name.toLowerCase(), command);
        }
    }

    private Command command;

    RegisteredCommand(Command command) {
        this.command = command;
        registerCommand(this.getCommand());
    }

    public static Collection<Command> getCommands() {
        return commandMap.values();
    }

    public Command getCommand() {
        return command;
    }


}
