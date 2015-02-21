/*
 *  Created by Filip P. on 2/8/15 8:10 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Registrable;

import java.util.ArrayList;
import java.util.List;

public enum DefaultTrackers implements Registrable {

    KILLS("kills", 0),;

    private String id;
    private int    defaultValue;

    DefaultTrackers(String id, int defaultValue) {
        this.id = id;
        this.defaultValue = defaultValue;
        registerTracker();
    }

    private void registerTracker() {
        if (DEFAULT_TRACKERS == null) {
            DEFAULT_TRACKERS = new ArrayList<>();
        }
        DEFAULT_TRACKERS.add(this.tracker());
    }

    public static DefaultTrackers manager = null;

    public static void register() {
    }

    public Tracker tracker() {
        return new Tracker(id, defaultValue);
    }

    private static List<Tracker> DEFAULT_TRACKERS;

    public static List<Tracker> getDefaultTrackers() {
        return DEFAULT_TRACKERS;
    }

    public Tracker getTracker(CorePlayer corePlayer) {
        return corePlayer.getTracker(this.id);
    }

}
