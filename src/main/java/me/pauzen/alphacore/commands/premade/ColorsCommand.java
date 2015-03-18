/*
 *  Created by Filip P. on 3/13/15 11:33 PM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

@CommandMeta("colors")
public class ColorsCommand extends Command {

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(false) {
            
            @Override
            public void onRun() {
                
                commandSender.sendMessage("Possible Colors: ");

                for (ChatColor chatColor : ChatColor.values()) {
                    CorePlayer corePlayer = CorePlayer.get((Player) commandSender);
                    
                    corePlayer.sendJSON(
                        "{\n" +
                            "text:\"" + chatColor + "" + chatColor.name() + "\",\n" +
                            "clickEvent:{\n" +
                                "action:suggest_command,\n" +
                                "value:\"" + chatColor + "\"\n" +
                            "}\n" +
                        "}"
                    );
                }
            }
        };
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {

        if (e.getReason().toLowerCase().contains("char")) {
            
            e.setCancelled(true);
        }
    }
}
