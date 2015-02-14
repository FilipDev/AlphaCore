/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.listeners.ListenerRegisterer;
import me.pauzen.alphacore.utils.misc.Todo;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Registrable;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;
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
        Set<Class> tempRegistrables = new HashSet<>();
        getRegistrables().stream().forEach(clazz -> {

            if (ReflectionFactory.implementsInterface(clazz, Registrable.class)) {
                tempRegistrables.add(clazz);
            }
        });

        registerManagers(tempRegistrables);
        PremadeEffects.values();
        PremadeAbilities.values();
        ListenerRegisterer.register();
    }

    public void registerManagers(Set<Class> registrables) {
        registrables.forEach(this::registerManager);
    }

    public Set<Class> getRegistrables() {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        if (jarFile == null) {
            return null;
        }

        Enumeration<JarEntry> entries = jarFile.entries();

        Set<Class> classes = new HashSet<>();

        JarEntry currentEntry;
        while (true) {

            try {
                currentEntry = entries.nextElement();
            } catch (NoSuchElementException e) {
                break;
            }

            try {
                if (!currentEntry.getName().endsWith(".class")) {
                    continue;
                }

                String className = currentEntry.getName().substring(0, currentEntry.getName().length() - 6);
                className = className.replace("/", ".");
                
                Class clazz = this.getClass().getClassLoader().loadClass(className);
                clazz.getDeclaredFields();

                classes.add(clazz);
            } catch (ClassNotFoundException ignored) {
            }
        }
        return classes;
    }

    public void registerManager(Class<? extends Registrable> registrableClass) {
        try {
            ReflectionFactory.getMethod(registrableClass, "register").invoke(null);
            Registrable registrable = (Registrable) ReflectionFactory.getField(registrableClass, "manager").get(null);
            if (registrable != null) {
                registrables.add(registrable);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        core = null;
        registrables.forEach(Nullifiable::nullify);
    }

}
