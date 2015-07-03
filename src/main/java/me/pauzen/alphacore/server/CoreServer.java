/*
 *  Created by Filip P. on 5/4/15 7:10 PM.
 */

package me.pauzen.alphacore.server;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.places.CorePlace;
import me.pauzen.alphacore.places.Place;
import me.pauzen.alphacore.places.PlaceAction;

import java.util.HashSet;
import java.util.Set;

public class CoreServer extends ListenerImplementation {

    public static final Place SERVER_PLACE = new CorePlace("Server", null);

    private static CoreServer coreServer;
    
    public static CoreServer getCoreServer() {
        if (coreServer == null) {
            coreServer = new CoreServer();
        }
        return coreServer;
    }
    
    private Set<PlaceAction> disallowedActions = new HashSet<>();
    
    public void disallowAction(PlaceAction placeAction) {
        disallowedActions.add(placeAction);
    }
    
    public void reallowAction(PlaceAction placeAction) {
        disallowedActions.remove(placeAction);
    }
    
    public boolean isDisallowed(PlaceAction placeAction) {
        return disallowedActions.contains(placeAction);
    }
}
