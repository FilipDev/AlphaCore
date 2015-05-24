/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.abilities.RestrictionBypass;
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandListener {

    public Map<String, String> modifiers;
    public CommandSender       sender;
    public String[]            args;
    private List<String> testForPermissions = new ArrayList<>();
    private Command owner;
    private boolean canConsoleSend;
    private Map<UUID, Long> cooldowns = new HashMap<>();
    
    private ICommandListener listener;

    public CommandListener(ICommandListener listener, Command owner, boolean canConsoleSend, String... testForPermissions) {
        this.owner = owner;
        this.canConsoleSend = canConsoleSend;
        this.listener = listener;
        testForPermissions(testForPermissions);
    }
    
    public CommandListener(ICommandListener listener, Command owner, String... testForPermissions) {
        this(listener, owner, false, testForPermissions);
    }

    public CommandListener(ICommandListener listener, boolean canConsoleSend, String... testForPermissions) {
        this(listener, null, canConsoleSend, testForPermissions);
    }
    
    public CommandListener(ICommandListener listener, String... testForPermissions) {
        this(listener, null, false, testForPermissions);
    }


    public CommandListener(Command owner, boolean canConsoleSend, String... testForPermissions) {
        this(null, owner, canConsoleSend, testForPermissions);
    }
    
    public CommandListener(Command owner, String... testForPermissions) {
        this(null, owner, false, testForPermissions);
    }

    public CommandListener(boolean canConsoleSend, String... testForPermissions) {
        this(null, null, canConsoleSend, testForPermissions);
    }

    public CommandListener(String... testForPermissions) {
        this(null, null, false, testForPermissions);
    }

    public void testForPermissions(String... permissions) {
        Collections.addAll(testForPermissions, permissions);
    }

    public boolean preRun(Command command, CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        if (commandSender instanceof Player) {

            UUID uniqueId = ((Player) commandSender).getUniqueId();
            if (!cooledDown(uniqueId)) {
                ErrorMessage.COOLDOWN.send(commandSender, "Command", getRemaining(uniqueId) + "ms");
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

        setValues(commandSender, args, modifiers);
        onRun();
        clearValues();
        return true;
    }

    private void setValues(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        this.sender = commandSender;
        this.args = args;
        this.modifiers = modifiers;
    }

    public void sub(Command... commands) {
        if (owner != null) {
            owner.addSubCommands(commands);
        }
    }

    private void clearValues() {
        this.sender = null;
        this.args = null;
        this.modifiers = null;
    }

    public boolean canConsoleSend() {
        return this.canConsoleSend;
    }

    public void onRun() {
        if (listener != null) {
            listener.onRun(sender, modifiers, args);
        }
    }

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

    public Command getOwner() {
        return owner;
    }

    public void setOwner(Command owner) {
        this.owner = owner;
    }

    public Map<String, Command> getSubCommands() {
        return getOwner().getSubCommands();
    }
}
