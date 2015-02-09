/*
 *  Created by Filip P. on 2/7/15 11:08 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.players.CorePlayer;

public class Tracker {

    private int value = 0;
    private String id;

    public Tracker(String id, int initialValue) {
        this.id = id;
        this.value = initialValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addValue(int value) {
        this.value += value;
    }

    public void subtractValue(int points) {
        value -= points;
    }

    public int getValue() {
        return this.value;
    }

    public void addTracker(CorePlayer corePlayer) {
        corePlayer.getTrackers().put(id, this);
    }
}
