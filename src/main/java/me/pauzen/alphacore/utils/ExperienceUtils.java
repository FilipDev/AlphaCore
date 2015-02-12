/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.utils.misc.Range;

public final class ExperienceUtils {

    private ExperienceUtils() {
    }

    public static int getRequiredExperience(int level) {
        if (middleRange.isWithinInclusive(level)) {
            return 2 * level + 7;
        }
        if (initialRange.isWithinInclusive(level)) {
            return 5 * level - 38;
        }
        return 9 * level - 158;
    }

    private static Range initialRange = new Range(0, 15);
    private static Range middleRange  = new Range(16, 30);
}