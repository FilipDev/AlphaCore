/*
 *  Created by Filip P. on 4/7/15 12:15 AM.
 */

package me.pauzen.alphacore.messages.json.events;

import me.pauzen.alphacore.messages.json.Action;

public class ClickEvent extends JSONEvent {

    public ClickEvent(Action action, String value) {
        super(action, value);
    }

    @Override
    String name() {
        return "clickEvent";
    }
}
