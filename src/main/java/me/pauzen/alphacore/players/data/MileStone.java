/*
 *  Created by Filip P. on 2/9/15 5:32 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.trackers.Tracker;

public abstract class MileStone {

    private int value;

    public MileStone(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public abstract void onReach(CorePlayer corePlayer, Tracker tracker);

    public void add(Tracker tracker) {
        tracker.addMilestone(this);
    }
}
