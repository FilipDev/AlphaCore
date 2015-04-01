/*
 *  Created by Filip P. on 3/21/15 4:46 PM.
 */

package me.pauzen.alphacore.emitters;

import me.pauzen.alphacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public abstract class Emitter {

    private Location location;
    private long     frequency;

    public Emitter at(Location location) {
        this.location = location;
        return this;
    }

    public Emitter frequency(long frequency) {
        this.frequency = frequency;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public long getFrequency() {
        return frequency;
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getCore(), this::emit, getFrequency(), getFrequency());
    }

    public abstract void emit();

}
