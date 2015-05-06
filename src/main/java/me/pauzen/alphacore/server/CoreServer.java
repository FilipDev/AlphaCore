/*
 *  Created by Filip P. on 5/4/15 7:10 PM.
 */

package me.pauzen.alphacore.server;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.places.Place;

import java.util.HashSet;
import java.util.Set;

public class CoreServer extends ListenerImplementation {

    public static Place SERVER_PLACE = new Place("Server", null);

    public Set<Mode> activeModes = new HashSet<>();

}
