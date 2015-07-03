/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.commands.events.CommandRunEvent;
import me.pauzen.alphacore.commands.exceptions.NoPermissionException;
import me.pauzen.alphacore.commands.exceptions.NotCooledDownException;
import me.pauzen.alphacore.commands.exceptions.PermissionType;
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
    private Map<CommandSender, Long> cooldowns = new HashMap<>();

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

    public void testForPermissions(String... permissions) {
        Collections.addAll(testForPermissions, permissions);
    }

    public boolean preRun(Command command, CommandSender commandSender, String[] args, Map<String, String> modifiers) throws NoPermissionException, NotCooledDownException {
        if (commandSender instanceof Player) {

            if (!isCooledDown(commandSender)) {
                throw new NotCooledDownException();
            }

            CorePlayer corePlayer = CorePlayer.get((Player) commandSender);
            
            if (!corePlayer.getCurrentPlace().isAllowed(command)) {
                throw new NoPermissionException(PermissionType.PLACE);
            }
            
            if (testForPermissions != null) {
                for (String testForPermission : testForPermissions) {
                    if (!commandSender.hasPermission(testForPermission)) {
                        throw new NoPermissionException(PermissionType.PERMISSION);
                    }
                }
            }

            CommandRunEvent commandRunEvent = new CommandRunEvent(corePlayer, command);
            if (commandRunEvent.call().isCancelled()) {
                throw new NoPermissionException(PermissionType.SERVER);
            }
        }

        setValues(commandSender, args, modifiers);
        onRun();
        clean();
        return true;
    }

    private void setValues(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
        this.sender = commandSender;
        this.args = args;
        this.modifiers = modifiers;
    }

    private void clean() {
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

    public void addCooldown(CommandSender commandSender, long length) {
        cooldowns.put(commandSender, System.currentTimeMillis() + length);
    }

    public boolean isCooledDown(CommandSender commandSender) {
        boolean cooledDown = getRemaining(commandSender) - System.currentTimeMillis() <= 0;
        if (cooledDown) {
            cooldowns.remove(commandSender);
        }
        return cooledDown;
    }

    public long getRemaining(CommandSender commandSender) {
        return cooldowns.getOrDefault(commandSender, 0L) - System.currentTimeMillis();
    }

    public Command getOwner() {
        return owner;
    }

    public void setOwner(Command owner) {
        this.owner = owner;
    }

    public ICommandListener getListener() {
        return listener;
    }

    public void setListener(ICommandListener listener) {
        this.listener = listener;
    }
}
