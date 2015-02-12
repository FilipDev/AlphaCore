/*
 *  Created by Filip P. on 2/8/15 8:10 PM.
 */

package me.pauzen.alphacore.players.data;

import java.util.ArrayList;
import java.util.List;

public enum DefaultTrackers {

    KILLS("kills", 0),
    ;

    private String id;
    private int defaultValue;

    DefaultTrackers(String id, int defaultValue) {
        this.id = id;
        this.defaultValue = defaultValue;
        register();
    }
    
    private void register() {
        DEFAULT_TRACKERS.add(this.tracker());
    }

    public Tracker tracker() {
        return new Tracker(id, defaultValue);
    }

    private static List<Tracker> DEFAULT_TRACKERS = new ArrayList<>();
    
    public static List<Tracker> getDefaultTrackers() {
        return DEFAULT_TRACKERS;
    }
    
    public static void addDefaultTracker(Tracker tracker) {
        DEFAULT_TRACKERS.add(tracker);
    }

}
