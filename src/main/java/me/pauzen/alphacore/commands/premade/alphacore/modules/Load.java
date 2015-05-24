/*
 *  Created by Filip P. on 5/21/15 12:07 AM.
 */

package me.pauzen.alphacore.commands.premade.alphacore.modules;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.core.managers.Manager;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.messages.ErrorMessage;
import org.bukkit.ChatColor;

import java.util.Optional;

@CommandMeta("load")
public class Load extends Command {

    private static ChatMessage LOAD_MANAGER = new ChatMessage(ChatColor.GREEN + "Loaded Manager %s.");

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener("alphacore.modules.load") {
            @Override
            public void onRun() {
                Core core = Core.getCore();

                if (args.length == 0) {
                    ErrorMessage.INVALID_ARGUMENTS.send(sender, "Argument must be a valid Manager.");
                    return;
                }

                Optional<Class<Manager>> manager = Optional.ofNullable(core.getManagerClassMap().get(args[0]));

                if (manager.isPresent()) {
                    Class<Manager> managerClass = manager.get();
                    Core.getCore().registerManager(managerClass);
                    LOAD_MANAGER.send(sender, Core.getCore().getManagers().get(args[0]).getName());
                } else {
                    ErrorMessage.INVALID_ARGUMENTS.send(sender, "Argument must be a valid Manager.");
                }
            }
        };
    }
}