/*
 *  Created by Filip P. on 5/4/15 7:01 PM.
 */

package me.pauzen.alphacore.places;

import org.bukkit.Location;

public abstract class PhysicalPlace extends CorePlace {

    public PhysicalPlace(String name, Place superPlace) {
        super(name, superPlace);
    }

    public abstract boolean contains(Location location);
}
