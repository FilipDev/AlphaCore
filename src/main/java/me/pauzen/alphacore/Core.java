/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Core extends JavaPlugin {
    
    private static Core core;
    
    public static Core getCore() {
        return core;
    }

    private Set<Nullifiable> nullifiableManagers = new HashSet<>();

    @Override
    public void onEnable() {
        core = this;
        registerManagers(getManagers());
    }

    public void registerManagers(Set<Class> managerClasses) {
        managerClasses.forEach(this::registerManager);
    }
    
    public Set<Class> getManagers() {
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
        
        Set<Class> managers = new HashSet<>();
        
        JarEntry currentEntry;
        while ((currentEntry = entries.nextElement()) != null) {
            try {
                Class clazz = Class.forName(currentEntry.getName());

                for (Class<?> anInterface : clazz.getInterfaces()) {
                    if (anInterface.getClass().getName().equals(Nullifiable.class.getName())) {
                        managers.add(clazz);
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        return managers;
    }
    
    public void registerManager(Class managerClass) {
        try {
            ReflectionFactory.getMethod(managerClass, "registerManager").invoke(null);
            Nullifiable nullifiable = (Nullifiable) ReflectionFactory.getField(managerClass, "manager").get(null);
            nullifiableManagers.add(nullifiable);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        core = null;
        nullifiableManagers.forEach(Nullifiable::nullify);
    }

}
