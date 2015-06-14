/*
 *  Created by Filip P. on 4/10/15 2:48 PM.
 */

package me.pauzen.alphacore.messages.json;

import me.pauzen.alphacore.messages.json.events.JSONEvent;
import org.bukkit.ChatColor;

public class MessagePart {

    private StringBuilder part = new StringBuilder("    {\n").append("      text: \"%message%\"");

    private String message = "";

    public MessagePart(String message) {
        this.message = message;
    }

    public MessagePart() {
    }

    public MessagePart addEvent(JSONEvent event) {
        add(event.toString());
        return this;
    }

    public MessagePart setColor(ChatColor color) {
        add("color", color.name().toLowerCase());
        return this;
    }

    public MessagePart addFormat(ChatColor format) {
        if (!format.isFormat()) {
            throw new IllegalArgumentException("ChatColor must be a format.");
        }

        add(format.name().toLowerCase(), "true");
        return this;
    }

    public MessagePart add(String key, String value) {
        add(key + ":" + "'" + value + "'");
        return this;
    }

    private void add(String string) {
        if (!part.toString().equals("    {\n")) {
            part.append(",\n");
        }
        part.append(string);
    }

    public String getWhole() {
        return part.append("\n    }").toString().replace("%message%", message);
    }

    public String getMessage() {
        return message;
    }

    public MessagePart setMessage(String message) {
        this.message = message;
        return this;
    }
}
