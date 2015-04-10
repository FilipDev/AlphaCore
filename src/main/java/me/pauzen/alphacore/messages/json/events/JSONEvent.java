/*
 *  Created by Filip P. on 4/7/15 12:10 AM.
 */

package me.pauzen.alphacore.messages.json.events;

import me.pauzen.alphacore.messages.json.Action;

public abstract class JSONEvent {

    private Action action;
    private String value;

    public JSONEvent(Action action, String value) {
        this.action = action;
        this.value = value;
    }

    abstract String name();

    @Override
    public String toString() {
        return "      " + name() + ":{\n" +
                "        action:" + action.getAction() +
                ",\n        value:'" + value + "'\n" +
                "      }";
    }
}
