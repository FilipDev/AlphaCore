/*
 *  Created by Filip P. on 4/10/15 2:48 PM.
 */

package me.pauzen.alphacore.messages.json;

import me.pauzen.alphacore.messages.json.events.JSONEvent;

public class MessagePart {

    private StringBuilder part = new StringBuilder("    {\n").append("      text: \"%message%\"");

    private String message = "";

    public MessagePart(String message) {
        this.message = message;
    }

    public MessagePart() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addEvent(JSONEvent event) {
        add(event.toString());
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

}
