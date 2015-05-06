/*
 *  Created by Filip P. on 3/21/15 4:46 PM.
 */

package me.pauzen.alphacore.emitters;

import me.pauzen.alphacore.core.modules.ManagerModule;
import org.bukkit.Location;

public abstract class Emitter implements ManagerModule {

    private Location location;
    private long     frequency;
    private long lastRun = 0;

    public Emitter at(Location location) {
        this.location = location;
        return this;
    }

    public Emitter frequency(long frequency) {
        this.frequency = frequency;
        return this;
    }

    public boolean elapsed() {
        boolean elapsed = System.currentTimeMillis() - lastRun >= getFrequency() * 50;
        if (elapsed) {
            lastRun = System.currentTimeMillis();
        }
        return elapsed;
    }

    public Location getLocation() {
        return location;
    }

    public long getFrequency() {
        return frequency;
    }

    public abstract void emit();

    @Override
    public void unload() {
        EmitterManager.getManager().unregisterModule(this);
    }

}
