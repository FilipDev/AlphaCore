/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.Bukkit;

public final class UnsafeBukkitClasses {

    private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    private UnsafeBukkitClasses() {
    }

    public static Class getNMSClass(String className) {
        return getTheClass("net.minecraft.server.", className);
    }

    public static Class getOBCClass(String className) {
        return getTheClass("org.bukkit.craftbukkit.", className);
    }

    private static Class getTheClass(String path, String name) {
        return ReflectionFactory.classFromName(path + VERSION + "." + name);
    }
}
