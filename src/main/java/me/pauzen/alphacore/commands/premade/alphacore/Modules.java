/*
 *  Created by Filip P. on 3/8/15 1:22 AM.
 */

package me.pauzen.alphacore.commands.premade.alphacore;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import org.bukkit.ChatColor;

public class Modules extends Command {
    @Override
    public String getName() {
        return "modules";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(true, "alphacore.admin") {

            @Override
            public void onRun() {
                Core.getCore().getManagers().forEach(reg -> commandSender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Manager: " + ChatColor.RESET + reg.getClass().getName()));
            }
        };
    }
}
