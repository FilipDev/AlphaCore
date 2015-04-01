/*
 *  Created by Filip P. on 3/17/15 5:49 PM.
 */

package me.pauzen.alphacore.utils.json;

public enum Property {

    HOVER_EVENT("hoverEvent"),;

    private String name;

    Property(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String asJSON(String value) {
        return name + ": " + value;
    }

}