/*
 *  Created by Filip P. on 4/7/15 12:00 AM.
 */

package me.pauzen.alphacore.messages.json;

public enum PlaceHolder {
    
    MESSAGE("%m"),
    ADDITIONAL_VALUE("%v"),
    ACTION("%a")
    ;

    private String placeholder;

    PlaceHolder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }
}
