/*
 *  Created by Filip P. on 3/8/15 1:22 AM.
 */

package me.pauzen.alphacore.commands.premade.alphacore;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.commands.premade.alphacore.modules.Reload;
import me.pauzen.alphacore.commands.premade.alphacore.modules.Unload;
import org.bukkit.ChatColor;

@CommandMeta("modules")
public class Modules extends Command {

    @Override
    public void onRegister() {
        addSubCommands(new Unload(), new Reload());
    }

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener(null, true, "alphacore.modules") {

            @Override
            public void onRun() {
                String managersHeader = ChatColor.GREEN + "" + ChatColor.BOLD + "Managers: ";
                StringBuilder managers = new StringBuilder();
                for (String name : Core.getManagerHandler().getManagers().keySet()) {
                    if (!managers.toString().isEmpty()) {
                        managers.append(", ");
                    }
                    managers.append(name);
                }

                sender.sendMessage(managersHeader + ChatColor.WHITE + managers.toString());
            }
        };
    }
}
