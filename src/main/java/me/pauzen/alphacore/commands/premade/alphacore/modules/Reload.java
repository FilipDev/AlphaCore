/*
 *  Created by Filip P. on 5/20/15 11:57 PM.
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

@CommandMeta("reload")
public class Reload extends Command {

    private static ChatMessage RELOAD_MANAGER = new ChatMessage(ChatColor.GREEN + "Reloaded Manager %s.");

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener("alphacore.modules.reload") {
            @Override
            public void onRun() {
                Core core = Core.getCore();

                if (args.length == 0) {
                    ErrorMessage.INVALID_ARGUMENTS.send(sender, "Argument must be a valid Manager.");
                    return;
                }

                Optional<Manager> manager = Optional.ofNullable(core.getManagers().get(args[0]));

                if (manager.isPresent()) {
                    Manager manager1 = manager.get();
                    Core.unregisterManager(manager1);
                    Core.registerManager(manager1);
                    RELOAD_MANAGER.send(sender, manager1.getName());
                } else {
                    ErrorMessage.INVALID_ARGUMENTS.send(sender, "Argument must be a valid Manager.");
                }
            }
        };
    }
}