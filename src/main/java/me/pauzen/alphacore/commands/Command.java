/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.messages.ErrorMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Command {

    private List<CommandListener> commandListeners = new ArrayList<>();

    public void execute(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        for (CommandListener commandListener : this.commandListeners) {
            if (!commandListener.canConsoleSend()) {
                if (!(commandSender instanceof Player)) {
                    ErrorMessage.CONSOLESENDER.sendMessage(commandSender);
                    return;
                }
            }
            commandListener.preRun(commandSender, args, modifiers);
        }
    }

    public Command() {
        addListener(defaultListener());
    }

    public void addListener(CommandListener commandListener) {
        this.commandListeners.add(commandListener);
    }

    public abstract String getName();

    public abstract CommandListener defaultListener();

}
