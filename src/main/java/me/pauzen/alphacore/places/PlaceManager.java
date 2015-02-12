/*
 *  Created by Filip P. on 2/10/15 7:09 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.Location;

public class PlaceManager implements Registrable {

    @Nullify
    private static Place DEFAULT_PLACE = new Place("DEFAULT") {
        @Override
        public boolean contains(Location location) {
            return true;
        }
    };
    @Nullify
    private static PlaceManager manager;

    public static PlaceManager getManager() {
        return manager;
    }

    public static void register() {
        manager = new PlaceManager();
    }
}
