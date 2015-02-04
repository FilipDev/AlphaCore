/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class CommandListener {

    private List<String> testForPermissions;

    public CommandListener(String... permissions) {
        this.testForPermissions = new ArrayList<>(permissions.length);
        Collections.addAll(testForPermissions, permissions);
    }

    public void onRunPreTests(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        if (testForPermissions == null) {
            onRun(commandSender, args, modifiers);
        } else {
            for (String testForPermission : testForPermissions) {
                if (!commandSender.hasPermission(testForPermission)) {
                    return;
                }
            }
            onRun(commandSender, args, modifiers);
        }
    }
    
    public abstract boolean canConsoleSend();
    
    public abstract void onRun(CommandSender commandSender, String[] args, Map<String, String> modifiers);
    
}
