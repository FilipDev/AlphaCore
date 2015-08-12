/*
 *  Created by Filip P. on 5/4/15 7:01 PM.
 */

package me.pauzen.alphacore.places.places;

import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.places.actions.PhysicalAction;
import org.bukkit.Location;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class PhysicalPlace extends CorePlace {

    private Set<PhysicalAction> disallowedActions = new HashSet<>();

    public PhysicalPlace(String name, Place superPlace) {
        super(name, superPlace);
    }

    @Override
    public boolean isPhysical() {
        return true;
    }

    public abstract boolean contains(Location location);

    public boolean isAllowed(PhysicalAction physicalAction) {
        return disallowedActions.contains(physicalAction);
    }
    
    public void disallow(PhysicalAction... physicalActions) {
        Collections.addAll(disallowedActions, physicalActions);
    }
}
