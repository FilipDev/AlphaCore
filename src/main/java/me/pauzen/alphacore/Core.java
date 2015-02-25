/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.listeners.ListenerRegisterer;
import me.pauzen.alphacore.updater.LoadPriority;
import me.pauzen.alphacore.updater.Priority;
import me.pauzen.alphacore.utils.misc.Todo;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.jar.JarFile;

public class Core extends JavaPlugin {

    private static Core core;

    public static Core getCore() {
        return core;
    }

    private List<Registrable> registrables = new ArrayList<>();

    @Todo("Fix support for reloading")
    @Override
    public void onEnable() {
        core = this;

        generatePriorities();

        getRegistrables();

        registerManagers();
        
        PremadeAbilities.values();
        PremadeEffects.values();
        ListenerRegisterer.register();
    }

    @Override
    public void onDisable() {
        core = null;
        registrables.forEach(Nullifiable::nullify);
    }

    private EnumMap<LoadPriority, List<Class>> loadPriorityList = new EnumMap<>(LoadPriority.class);

    public void generatePriorities() {
        for (LoadPriority loadPriority : LoadPriority.values()) {
            loadPriorityList.put(loadPriority, new ArrayList<>());
        }
    }

    public void registerManagers() {
        loadPriorityList.entrySet().forEach((entry) -> {
            entry.getValue().forEach(this::registerManager);
        });
    }

    public void getRegistrables() {
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
                clazz = this.getClass().getClassLoader().loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            Priority annotation = (Priority) clazz.getAnnotation(Priority.class);

            LoadPriority loadPriority;

            if (annotation == null) {
                loadPriority = LoadPriority.NORMAL;
            } else {
                loadPriority = annotation.value();
            }

            if (!ReflectionFactory.implementsInterface(clazz, Registrable.class)) {
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

}
