/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.abilities.RestrictionBypass;
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class CommandListener {

    private static ErrorMessage NOT_COOLEDDOWN = new ErrorMessage("Command is still on cooldown." + ChatColor.RED + " (%s remaining)");
    public Map<String, String> modifiers;
    public CommandSender       commandSender;
    public String[]            args;
    private List<String> testForPermissions = new ArrayList<>();
    private boolean canConsoleSend;
    private Map<String, Command> subCommands = new HashMap<>();
    private Map<UUID, Long>      cooldowns   = new HashMap<>();

    public CommandListener(boolean canConsoleSend, String... testForPermissions) {
        this.canConsoleSend = canConsoleSend;
        testForPermissions(testForPermissions);
    }

    public CommandListener(String... testForPermissions) {
        this(true, testForPermissions);
    }

    public void testForPermissions(String... permissions) {
        Collections.addAll(testForPermissions, permissions);
    }

    public Map<String, Command> getSubCommands() {
        return subCommands;
    }

    public boolean preRun(Command command, CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        if (commandSender instanceof Player) {

            UUID uniqueId = ((Player) commandSender).getUniqueId();
            if (!cooledDown(uniqueId)) {
                NOT_COOLEDDOWN.send(commandSender, getRemaining(uniqueId) + "ms");
                return false;
            }

            CorePlayer corePlayer = CorePlayer.get((Player) commandSender);
            if (!corePlayer.hasActivated(RestrictionBypass.class)) {
                if (corePlayer.getCurrentPlace().shouldRun(command)) {
                    if (testForPermissions != null) {
                        for (String testForPermission : testForPermissions) {
                            if (!commandSender.hasPermission(testForPermission)) {
                                ErrorMessage.PERMISSIONS.send(commandSender, "this command");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        if (args.length != 0) {
            Command subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                CommandManager.getManager().executeCommand(subCommand, commandSender, args);
                return true;
            }
        }

        setValues(commandSender, args, modifiers);
        onRun();
        clearValues();
        return true;
    }

    private void setValues(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        this.commandSender = commandSender;
        this.args = args;
        this.modifiers = modifiers;
    }

    public void sub(Command... commands) {
        for (Command command : commands) {
            for (String name : command.getNames()) {
                subCommands.put(name, command);
            }
        }
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

    public List<String> getPermissions() {
        return testForPermissions;
    }

    public void addCooldown(UUID uuid, long length) {
        cooldowns.put(uuid, System.currentTimeMillis() + length);
    }

    public void addCooldown(CommandSender commandSender, long length) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            addCooldown(player.getUniqueId(), length);
        }
    }

    public boolean cooledDown(UUID uuid) {
        boolean cooledDown = cooldowns.getOrDefault(uuid, 0L) - System.currentTimeMillis() <= 0;
        if (cooledDown) {
            cooldowns.remove(uuid);
        }
        return cooledDown;
    }

    public long getRemaining(UUID uuid) {
        return cooldowns.getOrDefault(uuid, 0L) - System.currentTimeMillis();
    }


}
