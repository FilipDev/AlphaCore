/*
 *  Created by Filip P. on 4/6/15 9:37 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.messages.json.Action;

public final class JSONFormatter {

    private JSONFormatter() {
    }

    public static JSONMessage addHover(String message, Action action, String value) {
        return format("hoverEvent", message, action, value);
    }

    public static JSONMessage addClick(String message, Action action, String value) {
        return format("clickEvent", message, action, value);

    }

    public static JSONMessage format(String eventType, String message, Action action, String value) {
        JSONMessage hover = new JSONMessage("jsonevent");
        hover.setMessage(hover.getMessage().replace("%event%", eventType).replace("%message%", message).replace("%action%", action.getAction()).replace("%value%", value));
        return hover;
    }
}
