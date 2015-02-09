/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.misc;

import java.util.Random;

public class Range {

    private double min, max;

    public Range(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean isWithinInclusive(double value) {
        return value >= min && value <= max;
    }

    public boolean isWithinExclusive(double value) {
        return value > min && value < max;
    }

    public static Range toRange(double min, double max) {
        return new Range(min, max);
    }

    public static Range toRange(int min, int max) {
        return new Range(min, max);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(min);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    private static Random random = RandomProvider.getRandom();

    public float randomValue() {
        float value = ((float) random.nextInt((int) (max * 100000000.0F) - (int) (min * 100000000.0F)) / 100000000.0F + (float) min);
        return value;
    }
    
    public static float random(double min, double max) {
        return new Range(min, max).randomValue();
    }
}
