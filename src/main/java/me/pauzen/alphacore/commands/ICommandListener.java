/*
 *  Created by Filip P. on 5/20/15 7:55 PM.
 */

package me.pauzen.alphacore.commands;

import org.bukkit.command.CommandSender;

import java.util.Map;

public interface ICommandListener {

    public void onRun(CommandSender sender, Map<String, String> modifers, String[] args);

}
