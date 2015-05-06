/*
 *  Created by Filip P. on 3/8/15 1:22 AM.
 */

package me.pauzen.alphacore.commands.premade.alphacore;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import org.bukkit.ChatColor;

@CommandMeta("modules")
public class Modules extends Command {

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(true, "alphacore.admin") {

            {
                sub(new Command("unload") {
                    @Override
                    public CommandListener defaultListener() {
                        return new CommandListener("alphacore.admin") {
                            @Override
                            public void onRun() {
                                //TODO: Make this work.
                            }
                        };
                    }
                });
            }
            
            @Override
            public void onRun() {
                String managersHeader = ChatColor.GREEN + "" + ChatColor.BOLD + "Managers: ";
                StringBuilder managers = new StringBuilder();
                for (String name : Core.getCore().getManagers().keySet()) {
                    if (!managers.toString().isEmpty()) {
                        managers.append(", ");
                    }
                    managers.append(name);
                }
                commandSender.sendMessage(managersHeader + ChatColor.WHITE + managers.toString());
            }
        };
    }
}
