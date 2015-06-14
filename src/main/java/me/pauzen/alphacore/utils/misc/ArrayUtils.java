/*
 *  Created by Filip P. on 3/28/15 7:32 PM.
 */

package me.pauzen.alphacore.utils.misc;

import me.pauzen.alphacore.utils.misc.test.Test;

import java.util.Optional;

public final class ArrayUtils {

    private ArrayUtils() {
    }

    public static boolean contains(Object[] objects, Object object) {
        for (Object o : objects) {
            if (o == object) {
                return true;
            }
        }

        return false;
    }

    public static boolean weakContains(Object[] objects, Object object) {
        for (Object o : objects) {
            if (o.equals(object)) {
                return true;
            }
        }

        return false;
    }

    public static <T> Optional<T> getElement(T[] array, int index) {
        if (!Test.args(array, index + 1)) {
            return Optional.empty();
        }
        
        return Optional.of(array[index]);
    }

    public static int firstEmpty(Object[] someArray) {
        for (int i = someArray.length - 1; i >= 0; i--) {
            if (someArray[i] != null) {
                return i + 1;
            }
        }

        return -1;
    }

}
