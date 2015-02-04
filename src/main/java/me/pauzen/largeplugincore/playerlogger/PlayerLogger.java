/*
 *  Created by Filip P. on 2/2/15 11:25 PM.
 */

package me.pauzen.largeplugincore.playerlogger;

import me.pauzen.largeplugincore.players.MyPlayer;

public class PlayerLogger {

    private MyPlayer myPlayer;
    private int playTime = 0;

    public PlayerLogger(MyPlayer myPlayer) {
        this.myPlayer = myPlayer;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public void addPlaytime(int playTime) {
        this.playTime += playTime;
    }

    public void addSecond() {
        this.playTime++;
    }

    public void addMinute() {
        this.playTime += 60;
    }

    public void addHour() {
        this.playTime += 3600;
    }

    public int getPlayTime() {
        return this.playTime;
    }
    
    
    
    
}
