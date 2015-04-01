/*
 *  Created by Filip P. on 3/8/15 1:22 AM.
 */

package me.pauzen.alphacore.commands.alphacore;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.ChatColor;

@CommandMeta("modules")
public class Modules extends Command {

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(true, "alphacore.admin") {

            @Override
            public void onRun() {
                String managersHeader = ChatColor.GREEN + "" + ChatColor.BOLD + "Managers: ";
                StringBuilder managers = new StringBuilder();
                for (Registrable registrable : Core.getCore().getManagers()) {
                    if (!managers.toString().isEmpty()) {
                        managers.append(", ");
                    }
                    managers.append(registrable.getClass().getSimpleName());
                }
                commandSender.sendMessage(managersHeader + ChatColor.WHITE + managers.toString());
            }
        };
    }
}
