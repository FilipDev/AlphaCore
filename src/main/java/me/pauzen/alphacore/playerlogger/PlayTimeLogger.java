/*
 *  Created by Filip P. on 2/2/15 11:25 PM.
 */

package me.pauzen.alphacore.playerlogger;

import me.pauzen.alphacore.players.data.Tracker;

public class PlayTimeLogger extends Tracker {

    private static final String ID = "playtime";

    public PlayTimeLogger(int playTime) {
        super(ID, playTime);
    }

    public void setPlayTime(int playTime) {
        setValue(playTime);
    }

    public void addPlaytime(int playTime) {
        setPlayTime(getPlayTime() + playTime);
    }

    public void addSecond() {
        addPlaytime(1);
    }

    public void addMinute() {
        addPlaytime(60);
    }

    public void addHour() {
        addPlaytime(3600);
    }

    public int getPlayTime() {
        return getValue();
    }


}
