/*
 *  Created by Filip P. on 4/7/15 12:14 AM.
 */

package me.pauzen.alphacore.messages.json.events;

import me.pauzen.alphacore.messages.json.Action;

public class HoverEvent extends JSONEvent {

    public HoverEvent(Action action, String value) {
        super(action, value);
    }

    @Override
    String name() {
        return "hoverEvent";
    }
}
