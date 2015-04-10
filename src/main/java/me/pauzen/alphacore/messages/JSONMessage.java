/*
 *  Created by Filip P. on 3/17/15 10:42 AM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.io.streams.StringReader;
import me.pauzen.alphacore.utils.reflection.jar.JAREntryFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Deprecated
public class JSONMessage implements Message {

    private String message;

    public JSONMessage(String name) {
        this.message = StringReader.read(Core.getZipped("json/" + name + ".json").stream()).getContents();
    }

    public JSONMessage(JAREntryFile jar) {
        this.message = StringReader.read(jar.stream()).getContents();
    }

    @Override
    public String getPrefix() {
        return "";
    }

    @Override
    public String getRawMessage() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void send(CommandSender commandSender, String... strings) {
        if (commandSender instanceof Player) {
            send(CorePlayer.get((Player) commandSender), strings);
        }
        else {
            commandSender.sendMessage(String.format(getMessage(), strings));
        }
    }

    @Override
    public void send(CorePlayer corePlayer, String... strings) {
        corePlayer.sendJSON(String.format(getMessage(), strings));
    }

    @Override
    public void sendRawMessage(CommandSender commandSender, String... strings) {
        commandSender.sendMessage(String.format(getMessage(), strings));
    }

    @Override
    public void sendRawMessage(CorePlayer corePlayer, String... strings) {
        sendRawMessage(corePlayer.getPlayer(), strings);
    }
}
