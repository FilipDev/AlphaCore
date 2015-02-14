/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.command.CommandSender;

public interface Message {

    String getPrefix();

    String getRawMessage();

    default String getMessage() {
        return getPrefix() + getRawMessage();
    }

    default void sendMessage(CommandSender commandSender, Object... objects) {
        if (!new MessageSendEvent(commandSender).call().isCancelled()) {
            commandSender.sendMessage(String.format(this.toString(), objects));
        }
    }

    default void sendMessage(CorePlayer CorePlayer, Object... objects) {
        sendMessage(CorePlayer.getPlayer(), objects);
    }
}
