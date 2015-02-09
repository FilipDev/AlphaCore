/*
 *  Created by Filip P. on 2/8/15 8:10 PM.
 */

package me.pauzen.alphacore.players.data;

public enum DefaultTrackers {
    
    KILLS("kills", 0),
    ;
    
    private Tracker tracker;
    
    DefaultTrackers(String id, int defaultValue) {
        this.tracker = new Tracker(id, defaultValue);
    }
    
    public Tracker tracker() {
        return this.tracker;
    }
    
}
