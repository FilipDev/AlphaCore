/*
 *  Created by Filip P. on 2/2/15 11:13 PM.
 */

package me.pauzen.alphacore.updater;

public enum UpdateType {

    TICK(1L),
    SECOND(20L),
    MINUTE(1200L),
    TEM_MINUTES(12000),;

    private long ticks;
    private long lastTime;

    UpdateType(long ticks) {
        this.ticks = ticks * 50L;
        this.lastTime = System.currentTimeMillis();
    }

    public static boolean elapsed(long time, long required) {
        return System.currentTimeMillis() - time >= required;
    }

    public long getTicks() {
        return ticks;
    }

    protected boolean elapsed() {
        if (elapsed(this.lastTime, this.ticks)) {
            this.lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
