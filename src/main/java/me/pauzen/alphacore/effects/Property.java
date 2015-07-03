/*
 *  Created by Filip P. on 6/28/15 12:56 AM.
 */

package me.pauzen.alphacore.effects;

public class Property {

    public static Property INVISIBLE = new Property();
    public static Property TEMPORARY = new Property();

    Property() {
    }

    public void add(AppliedEffect effect) {
        effect.getProperties().add(this);
    }
}
