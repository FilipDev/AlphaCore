/*
 *  Created by Filip P. on 2/14/15 9:07 PM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandMeta("feed")
public class FeedCommand extends Command {

    private static ChatMessage FED = new ChatMessage(ChatColor.GOLD + "Your appetite has been sated.");

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(false, "core.feed") {
            @Override
            public void onRun() {
                Player target = args.length == 0 ? (Player) commandSender : Bukkit.getPlayer(args[0]);
                CorePlayer.get(target).feed();
                FED.send(commandSender);
            }
        };
    }
}
