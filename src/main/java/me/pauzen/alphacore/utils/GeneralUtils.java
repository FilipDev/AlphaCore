/*
 *  Created by Filip P. on 2/7/15 1:02 PM.
 */

package me.pauzen.alphacore.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

    public static int firstEmpty(Object[] someArray) {
        for (int i = someArray.length - 1; i >= 0; i--) {
            if (someArray[i] != null) {
                return i + 1;
            }
        }

        return -1;
    }
    
    public static String addSpacing(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i != 0) {
                builder.append(" ");
            }
            
            builder.append(strings[i]);
        }
        
        return builder.toString();
    }

    public static ItemMeta getMeta(ItemStack itemStack) {

        if (!itemStack.hasItemMeta()) {
            itemStack.setItemMeta(Bukkit.getItemFactory().getItemMeta(itemStack.getType()));
        }

        return itemStack.getItemMeta();
    }

}
