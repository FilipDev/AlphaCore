/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.largeplugincore.utils;

import org.bukkit.Bukkit;

public final class UnsafeBukkitClasses {

    private UnsafeBukkitClasses() {
    }

    private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];

    public static Class getNMSClass(String className)
    {
        return getTheClass("net.minecraft.server.", className);
    }

    public static Class getOBCClass(String className)
    {
        return getTheClass("org.bukkit.craftbukkit.", className);
    }
    
    private static Class getTheClass(String path, String name) {
        try {
            return Class.forName(path + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
