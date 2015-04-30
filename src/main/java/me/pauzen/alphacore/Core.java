/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.listeners.ListenerRegisterer;
import me.pauzen.alphacore.players.PlayerManager;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.misc.Todo;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import me.pauzen.alphacore.utils.reflection.Registrable;
import me.pauzen.alphacore.utils.reflection.jar.JAREntryFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarFile;

public class Core extends JavaPlugin implements Listener {

    private static Core core;
    private List<Registrable>                  registrables     = new ArrayList<>();
    private EnumMap<LoadPriority, List<Class>> loadPriorityList = new EnumMap<>(LoadPriority.class);

    private Map<JavaPlugin, List<AlphaCoreModule>> elements = new HashMap<>();

    public static Core getCore() {
        return core;
    }

    public static File getData() {
        return Core.getCore().getDataFolder();
    }

    public static JAREntryFile getZipped(String name) {
        return new JAREntryFile(name);
    }

    @Todo("Fix support for reloading")
    @Override
    public void onEnable() {
        core = this;
        
        Bukkit.getPluginManager().registerEvents(this, this);

        generatePriorities();

        computeRegisterables(loadPriorityList);

        registerManagers();

        PremadeEffects.values();
        ListenerRegisterer.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerManager.getManager().registerPlayer(player);
        }
    }
    
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        getModules().getOrDefault(event.getPlugin(), new ArrayList<>()).forEach(AlphaCoreModule::unload);
    }

    @Override
    public void onDisable() {
        registrables.forEach(Nullifiable::nullify);
        core = null;
    }

    public void generatePriorities() {
        for (LoadPriority loadPriority : LoadPriority.values()) {
            loadPriorityList.put(loadPriority, new ArrayList<>());
        }
    }

    public void registerManagers() {
        loadPriorityList.entrySet().forEach((entry) -> entry.getValue().forEach(this::registerManager));
    }

    public void computeRegisterables(EnumMap<LoadPriority, List<Class>> loadPriorityList) {

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        if (jarFile == null) {
            return;
        }

        jarFile.stream().filter((currentEntry) -> currentEntry.getName().endsWith(".class")).forEach((currentEntry) -> {
            String className = currentEntry.getName().substring(0, currentEntry.getName().length() - 6);
            className = className.replace("/", ".");

            Class clazz = null;
            try {
                clazz = Class.forName(className, true, getClass().getClassLoader());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            if (!Registrable.class.isAssignableFrom(clazz)) {
                return;
            }

            Priority annotation = (Priority) clazz.getAnnotation(Priority.class);

            LoadPriority loadPriority;

            if (annotation == null) {
                loadPriority = LoadPriority.NORMAL;
            }
            else {
                loadPriority = annotation.value();
            }

            if (clazz == Registrable.class) {
                return;
            }

            if (loadPriority == LoadPriority.NEVER) {
                return;
            }

            loadPriorityList.get(loadPriority).add(clazz);
        });
    }

    public void registerManager(Class clazz) {
        try {
            ReflectionFactory.getMethod(clazz, "register").invoke(null);
            Registrable registrable = (Registrable) ReflectionFactory.getField(clazz, "manager").get(null);
            if (registrable != null) {
                registrables.add(registrable);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public List<Registrable> getManagers() {
        return registrables;
    }

    public Map<JavaPlugin, List<AlphaCoreModule>> getModules() {
        return elements;
    }

    public static void registerModule(JavaPlugin javaPlugin, AlphaCoreModule alphaCoreModule) {
        getCore().getModules().putIfAbsent(javaPlugin, new ArrayList<>());
        getCore().getModules().get(javaPlugin).add(alphaCoreModule);
    }
}
