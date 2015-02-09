/*
 *  Created by Filip P. on 2/7/15 2:22 PM.
 */

package me.pauzen.alphacore.utils.misc;

import java.util.Random;

public final class RandomProvider {

    private static final Random random = new Random();

    /**
     * Prevents initialization.
     */
    private RandomProvider() {
    }

    /**
     * Provides a static instance of a Random object.
     *
     * @return Static instance of Random object.
     */
    public static Random getRandom() {
        return random;
    }
}
