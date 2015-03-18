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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandMeta(value = "help", aliases = {"?"}, description = "Gets all AlphaCore commands.")
public class Help extends Command {

    private String defaultDescription = "AlphaCore command.";
    
    @Override
    public CommandListener defaultListener() {
        return new CommandListener(true) {
            @Override
            public void onRun() {

                if (args.length < 2) {
                    for (Command command : CommandManager.getManager().getCommands()) {
                        send(commandSender, command.getName(), getDescription(command));
                        for (String alias : command.getAliases()) {
                            send(commandSender, alias, ChatColor.RED + "" + ChatColor.BOLD + "[ALIAS] " + ChatColor.RESET + getDescription(command));
                        }
                    }
                } else {
                    Command command = CommandManager.getManager().getCommand(args[1]);

                    if (command == null) {
                        ErrorMessage.COMMAND_NOT_FOUND.send(commandSender, args[1]);
                        return;
                    }

                    for (CommandListener commandListener : command.getListeners()) {
                        for (Map.Entry<String, Command> entry : commandListener.getSubCommands().entrySet()) {
                            send(commandSender, command.getName() + " " + entry.getKey(), getDescription(entry.getValue()));
                        }
                    }

                }
            }
        };
    }

    private JSONMessage jsonMessage = new JSONMessage("help");

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
