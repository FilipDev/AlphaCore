/*
 *  Created by Filip P. on 6/28/15 12:52 AM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.players.CorePlayer;

import java.util.HashSet;
import java.util.Set;

public class AppliedEffect {

    private final Effect     effect;
    private final long       duration;
    private final int        level;
    private final CorePlayer corePlayer;
    private final long       timestamp;

    private final Set<Property> properties = new HashSet<>();

    /**
     * @param corePlayer
     * @param level
     * @param effect
     * @param duration   Time effect is applied for in ticks.
     */
    public AppliedEffect(CorePlayer corePlayer, int level, Effect effect, long duration) {
        this.corePlayer = corePlayer;
        this.level = level;
        this.effect = effect;
        this.duration = duration;
        timestamp = System.currentTimeMillis();
    }

    public Effect getEffect() {
        return effect;
    }

    public int getLevel() {
        return level;
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }

    public long getDuration() {
        return duration;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public boolean elapsed() {
        return System.currentTimeMillis() - timestamp > duration * 50;
    }

    public boolean hasProperty(Property property) {
        return properties.contains(property);
    }
}
