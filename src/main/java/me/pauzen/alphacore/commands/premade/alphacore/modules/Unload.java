/*
 *  Created by Filip P. on 5/8/15 2:30 PM.
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

@CommandMeta("unload")
public class Unload extends Command {

    private static ChatMessage UNLOAD_MANAGER = new ChatMessage(ChatColor.GREEN + "Unloaded Manager %s.");

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener("alphacore.modules.unload") {
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
                    UNLOAD_MANAGER.send(sender, manager1.getName());
                }
                else {
                    ErrorMessage.INVALID_ARGUMENTS.send(sender, "Argument must be a valid Manager.");
                }
            }
        };
    }
}
