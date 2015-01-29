package me.pauzen.bukkitcommonpluginapi.messages;

import me.pauzen.bukkitcommonpluginapi.players.MyPlayer;
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
