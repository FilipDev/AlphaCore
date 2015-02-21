/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class CommandListener {

    private List<String> testForPermissions = new ArrayList<>();
    private boolean canConsoleSend;

    public void testForPermissions(String... permissions) {
        Collections.addAll(testForPermissions, permissions);
    }

    public CommandListener(boolean canConsoleSend, String... testForPermissions) {
        this.canConsoleSend = canConsoleSend;
        testForPermissions(testForPermissions);
    }

    public CommandListener(String... testForPermissions) {
        this(true, testForPermissions);
    }

    public boolean preRun(Command command, CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        if (commandSender instanceof Player) {
            CorePlayer corePlayer = CorePlayer.get((Player) commandSender);
            if (!PremadeAbilities.BYPASS_RESTRICTIONS.ability().hasActivated(corePlayer)) {
                if (corePlayer.getCurrentPlace().shouldRun(command)) {
                    if (testForPermissions != null) {
                        for (String testForPermission : testForPermissions) {
                            if (!commandSender.hasPermission(testForPermission)) {
                                ErrorMessage.PERMISSIONS.sendMessage(commandSender, "this command");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        setValues(commandSender, args, modifiers);
        onRun();
        clearValues();
        return true;
    }

    public Map<String, String> modifiers;
    public CommandSender       commandSender;
    public String[]            args;

    private void setValues(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        this.commandSender = commandSender;
        this.args = args;
        this.modifiers = modifiers;
    }

    private void clearValues() {
        this.commandSender = null;
        this.args = null;
        this.modifiers = null;
    }

    public boolean canConsoleSend() {
        return this.canConsoleSend;
    }

    public abstract void onRun();

}
