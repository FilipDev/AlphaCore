/*
 *  Created by Filip P. on 2/22/15 9:09 AM.
 */

package me.pauzen.alphacore.doublejump;

import me.pauzen.alphacore.players.CorePlayer;

public class DoubleJump {

    private int allowedJumps;
    private int leftJumps;

    private double vectorFactor;

    private CorePlayer corePlayer;

    public DoubleJump(CorePlayer corePlayer, int allowedJumps, double vectorFactor) {
        this.allowedJumps = allowedJumps;
        this.vectorFactor = vectorFactor;
        this.leftJumps = allowedJumps;
        this.corePlayer = corePlayer;
    }

    public int getAllowedJumps() {
        return allowedJumps;
    }

    public int getLeftJumps() {
        return getAllowedJumps() == -1 ? 2 : leftJumps;
    }

    public double getVectorFactor() {
        return vectorFactor;
    }

    public void subtractJump() {
        setLeftJumps(getLeftJumps() - 1);
    } 
    
    public void setLeftJumps(int leftJumps) {
        this.leftJumps = leftJumps;
    }
    
    public boolean jump() {
        
        if (leftJumps == 0) {
            return false;
        }
        
        corePlayer.getPlayer().setVelocity(corePlayer.getPlayer().getLocation().getDirection().multiply(vectorFactor));
        
        subtractJump();
        
        return true;
    }
    
    public boolean canJump() {
        return getLeftJumps() != 0;
    }
    
    public void resetJumps() {
        setLeftJumps(getAllowedJumps());
    }
}
