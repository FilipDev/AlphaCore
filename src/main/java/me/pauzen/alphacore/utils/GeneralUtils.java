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

    public static <T> boolean toggleContainment(Collection<T> collection, T object) {
        if (collection.contains(object)) {
            collection.remove(object);
            return false;
        }
        else {
            collection.add(object);
            return true;
        }
    }

    public static <T, O> boolean toggleContainment(Map<T, O> map, T object, O val) {
        if (map.containsKey(object)) {
            map.remove(object);
            return false;
        }
        else {
            map.put(object, val);
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

    public static <T, O> boolean setContainment(Map<T, O> map, T object, boolean containment, O val) {
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
