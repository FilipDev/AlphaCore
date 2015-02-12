/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class VectorUtils {

    private VectorUtils() {
    }

    public static Vector getRequiredVector(Location a, Location b) {
        return b.toVector().subtract(a.toVector());
    }

}
