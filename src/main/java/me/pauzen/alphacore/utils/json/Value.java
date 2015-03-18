/*
 *  Created by Filip P. on 3/17/15 5:51 PM.
 */

package me.pauzen.alphacore.utils.json;

public enum Value {
    
    SUGGEST_COMMAND("suggest_command"),
    ;

    private String value;

    Value(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
