/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import me.pauzen.alphacore.utils.reflection.Registrable;
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
    private List<Class>       nullifiables = new ArrayList<>();

    @Override
    public void onEnable() {
        core = this;
        Set<Class> tempRegisterables = new HashSet<>();
        getClasses().stream().forEach(clazz -> {

            if (ReflectionFactory.implementsInterface(clazz, Nullifiable.class)) {
                nullifiables.add(clazz);
            } else if (ReflectionFactory.implementsInterface(clazz, Registrable.class)) {
                tempRegisterables.add(clazz);
                nullifiables.add(clazz);
            }
        });

        tempRegisterables.forEach(clazz -> {
            try {
                this.registrables.add((Registrable) clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        
        registerManagers(tempRegisterables);
    }

    public void registerManagers(Set<Class> registerables) {
        registerables.forEach(this::registerManager);
    }

    public Set<Class> getClasses() {
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
        while ((currentEntry = entries.nextElement()) != null) {
            try {
                Class clazz = Class.forName(currentEntry.getName());

                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }

    public void registerManager(Class<? extends Registrable> registerableClass) {
        try {
            ReflectionFactory.getMethod(registerableClass, "register").invoke(null);
            Registrable registrable = (Registrable) ReflectionFactory.getField(registerableClass, "manager").get(null);
            registrables.add(registrable);
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
