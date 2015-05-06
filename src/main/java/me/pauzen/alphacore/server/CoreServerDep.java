/*
 *  Created by Filip P. on 5/4/15 4:29 PM.
 */

package me.pauzen.alphacore.server;

import me.pauzen.alphacore.utils.UnsafeBukkitClasses;
import me.pauzen.alphacore.utils.reflection.Reflection;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CoreServerDep {

    public void setOnlineMode(boolean flag) {
        runMutator("setOnlineMode", flag);
    }

    public void setCommandBlocksEnabled(boolean flag) {
        Class<?> minecraftServerClass = UnsafeBukkitClasses.getNMSClass("MinecraftServer");
        Method getServer = ReflectionFactory.getMethod(minecraftServerClass, "getServer");

        try {
            Object server = getServer.invoke(null);

            Reflection<Object> serverReflection = new Reflection<>(server);
            Object propertyManager = serverReflection.getValue("propertyManager");
            Reflection<Object> propertyManagerReflection = new Reflection<>(propertyManager);
            Object properties = propertyManagerReflection.getValue("properties");
            Reflection<Object> propertyReflection = new Reflection<>(properties);
            propertyReflection.callMethod("setProperty", "enable-command-block", "true");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void runMutator(String method, boolean flag) {
        Class<?> minecraftServerClass = UnsafeBukkitClasses.getNMSClass("MinecraftServer");
        Method getServer = ReflectionFactory.getMethod(minecraftServerClass, "getServer");

        try {
            Object server = getServer.invoke(null);

            Reflection<Object> serverReflection = new Reflection<>(server);
            serverReflection.callMethod(method, flag);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}