/*
 *  Created by Filip P. on 3/28/15 7:32 PM.
 */

package me.pauzen.alphacore.utils.misc;

public final class ArrayUtils {

    private ArrayUtils() {}
    
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

}
