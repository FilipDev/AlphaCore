/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public interface Message {

    String getPrefix();

    String getRawMessage();

    default String getMessage() {
        return getPrefix() + getRawMessage();
    }

    default void send(CommandSender commandSender, String... strings) {
        if (!new MessageSendEvent(commandSender).call().isCancelled()) {
            commandSender.sendMessage(String.format(this.getMessage(), strings));
        }
    }

    default void send(CorePlayer corePlayer, String... strings) {
        send(corePlayer.getPlayer(), strings);
    }

    default void sendRawMessage(CommandSender commandSender, String... strings) {
        if (!new MessageSendEvent(commandSender).call().isCancelled()) {
            commandSender.sendMessage(String.format(this.getRawMessage(), strings));
        }
    }

    default void sendRawMessage(CorePlayer corePlayer, String... strings) {
        sendRawMessage(corePlayer.getPlayer(), strings);
    }

    default void sendConsole(String... strings) {
        send(Bukkit.getConsoleSender(), strings);
    }

    default void sendIntermittently(CommandSender commandSender, long delay, String... strings) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getCore(), () -> {
            send(commandSender, strings);
        }, 0, delay);
    }
}
