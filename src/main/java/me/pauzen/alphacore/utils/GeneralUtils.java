/*
 *  Created by Filip P. on 2/7/15 1:02 PM.
 */

package me.pauzen.alphacore.utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class GeneralUtils {

    public static String toFileName(String... path) {
        StringBuilder pathBuilder = new StringBuilder();

        for (String location : path) {
            pathBuilder.append(File.separatorChar);
            pathBuilder.append(location);
        }

        return pathBuilder.toString();
    }

    public static <T> boolean toggleContainment(Collection<T> map, T object) {
        if (map.contains(object)) {
            map.remove(object);
            return false;
        }
        else {
            map.add(object);
            return true;
        }
    }

    public static <T> boolean toggleContainment(Map<T, Integer> collection, T object, int val) {
        if (collection.containsKey(object)) {
            collection.remove(object);
            return false;
        }
        else {
            collection.put(object, val);
            return true;
        }
    }

    public static <T> boolean setContainment(Collection<T> collection, T object, boolean containment) {
        if (containment) {
            if (!collection.contains(object)) {
                collection.add(object);
            }
        }
        else {
            collection.remove(object);
        }
        return containment;
    }

    public static <T> boolean setContainment(Map<T, Integer> map, T object, boolean containment, int val) {
        if (containment) {
            if (!map.containsKey(object)) {
                map.put(object, val);
            }
        }
        else {
            map.remove(object);
        }
        return containment;
    }

}
