/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.core.ManagerHandler;
import me.pauzen.alphacore.core.modules.PluginModule;
import me.pauzen.alphacore.lagwatcher.Watcher;
import me.pauzen.alphacore.listeners.ListenerRegisterer;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

public class Core extends JavaPlugin implements Listener {

    private static Core core;
    private Map<JavaPlugin, List<PluginModule>> elements = new HashMap<>();

    private ManagerHandler managerHandler;

    public static Core getCore() {
        return core;
    }

    public static File getData() {
        return Core.getCore().getDataFolder();
    }

    public static JavaPlugin getCallerPlugin() {
        Class[] callerClasses = ReflectionFactory.getCallerClasses();
        Class clazz = callerClasses[1];
        for (int i = 0; i < callerClasses.length; i++) {
            System.out.println(i + ": " + callerClasses[i]);
        }
        return JavaPlugin.getProvidingPlugin(clazz);
    }

    public static JavaPlugin getOwner(Object object) {
        return JavaPlugin.getProvidingPlugin(object.getClass());
    }

    @Override
    public void onEnable() {
        core = this;
        managerHandler = new ManagerHandler();
        ReflectionFactory.setFinal(ReflectionFactory.getField(getClass(), "managerHandler"), true);
        try {
            managerHandler.load();
            managerHandler.loadManagers(new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
            managerHandler.registerManagers();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        ListenerRegisterer.register();
        Watcher.register(1);
    }

    @Override
    public void onDisable() {
        core = null;
    }

    @Deprecated
    public Map<JavaPlugin, List<PluginModule>> getModules() {
        return elements;
    }

    public static ManagerHandler getManagerHandler() {
        return getCore().managerHandler;
    }
}
