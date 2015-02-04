/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.messages;

import me.pauzen.largeplugincore.players.MyPlayer;
import org.bukkit.command.CommandSender;

public interface Message {

    String getPrefix();

    String getRawMessage();
    
    default String getMessage() {
        return getPrefix() + getRawMessage();
    }

    default void sendMessage(CommandSender commandSender, Object... objects) {
        commandSender.sendMessage(String.format(this.toString(), objects));
    }
    
    default void sendMessage(MyPlayer myPlayer, Object... objects) {
        sendMessage(myPlayer.getPlayer(), objects);
    }
}
