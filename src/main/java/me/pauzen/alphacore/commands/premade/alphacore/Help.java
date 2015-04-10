/*
 *  Created by Filip P. on 3/8/15 3:07 PM.
 */

package me.pauzen.alphacore.commands.premade.alphacore;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandManager;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.messages.JSONMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

@CommandMeta(value = "help", aliases = {"?"}, description = "Gets all AlphaCore commands.")
public class Help extends Command {

    private String defaultDescription = "AlphaCore command.";
    private JSONMessage jsonMessage = new JSONMessage("help");

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(true) {
            @Override
            public void onRun() {

                if (args.length < 2) {
                    for (Map.Entry<String, Command> command : CommandManager.getManager().getCommands().entrySet()) {
                        if (hasPermissions(commandSender, command.getValue().defaultListener().getPermissions())) {
                            if (ArrayUtils.contains(command.getValue().getAliases(), command.getKey())) {
                                send(commandSender, command.getKey(), ChatColor.RED + "" + ChatColor.BOLD + "[ALIAS] " + ChatColor.RESET + getDescription(command.getValue()));
                            }
                            else {
                                send(commandSender, command.getKey(), getDescription(command.getValue()));
                            }
                        }

                    }
                }
                else {
                    StringBuilder rest = new StringBuilder();

                    for (int i = 1; i < args.length; i++) {
                        rest.append(args[i]);
                        rest.append(" ");
                    }

                    String restArgs = rest.toString().trim();

                    Command command = CommandManager.getManager().getCommand(restArgs);

                    if (command == null) {
                        ErrorMessage.COMMAND_NOT_FOUND.send(commandSender, ChatColor.RED + "\"" + restArgs + "\"" + ChatColor.DARK_RED);
                        return;
                    }

                    boolean foundAny = false;
                    for (CommandListener commandListener : command.getListeners()) {
                        if (hasPermissions(commandSender, commandListener.getPermissions())) {
                            for (Map.Entry<String, Command> entry : commandListener.getSubCommands().entrySet()) {
                                if (hasPermissions(commandSender, entry.getValue().defaultListener().getPermissions())) {
                                    foundAny = true;
                                    send(commandSender, command.getName() + " " + entry.getKey(), getDescription(entry.getValue()));
                                }
                            }
                        }
                    }

                    if (!foundAny) {
                        commandSender.sendMessage(ChatColor.RED + "No subcommands found for this command chain (" + ChatColor.DARK_RED + restArgs + ChatColor.RED + ").");
                    }

                }
            }
        };
    }

    private boolean hasPermissions(CommandSender sender, List<String> permissions) {

        if (permissions.isEmpty()) {
            return true;
        }

        for (String permission : permissions) {
            if (sender.hasPermission(permission)) {
                return true;
            }
        }

        return false;
    }

    private void send(CommandSender commandSender, String name, String description) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            CorePlayer corePlayer = CorePlayer.get(player);
            jsonMessage.send(corePlayer, name + ChatColor.GRAY, name, ChatColor.RESET + description);
            return;
        }
        commandSender.sendMessage(ChatColor.RED + name + ChatColor.GRAY + ": " + ChatColor.WHITE + description);
    }

    private String getDescription(Command command) {
        return command.getDescription().replace("%default%", defaultDescription);
    }
}
