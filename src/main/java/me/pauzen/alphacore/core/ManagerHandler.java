/*
 *  Created by Filip P. on 6/27/15 11:24 PM.
 */

package me.pauzen.alphacore.core;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.core.managers.Manager;
import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerHandler {

    private Map<String, Manager>               managers         = new HashMap<>();
    private EnumMap<LoadPriority, List<Class>> loadPriorityList = new EnumMap<>(LoadPriority.class);

    public void loadManager(Class<? extends Manager> managerClass) {
        Logger logger = Core.getCore().getLogger();
        logger.log(Level.INFO, "Loading manager " + managerClass.getName() + "...");
        try {
            Field managerField = ReflectionFactory.getField(managerClass, "manager");
            managerField.set(null, managerClass.newInstance());
            Manager manager = (Manager) managerField.get(null);
            if (manager != null) {
                manager.onEnable();
                managers.put(manager.getName(), manager);
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error loading module " + managerClass.getName());
            e.printStackTrace();
            return;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded " + managerClass.getName());
    }

    public void unloadManager(Manager manager) {
        if (manager instanceof ModuleManager) {
            ((ModuleManager) manager).unregister();
        }
        if (manager instanceof ListenerImplementation) {
            ((ListenerImplementation) manager).unload();
        }
        manager.onDisable();
        manager.nullify();
        managers.remove(manager.getName());
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        for (Manager manager : getManagers().values()) {
            if (manager instanceof ModuleManager) {
                ModuleManager moduleManager = (ModuleManager) manager;
                for (Object object : moduleManager.getModules()) {
                    ManagerModule module = (ManagerModule) object;
                    JavaPlugin owner = module.getOwner();
                    if (owner != null) {
                        if (owner == event.getPlugin()) {
                            module.unload();
                        }
                    }
                }
            }
        }
    }

    public void unload() {
        Set<Map.Entry<String, Manager>> entries = new HashSet<>();
        entries.addAll(managers.entrySet());
        getManagers().values().forEach(this::unloadManager);
    }

    public void load() {
        generatePriorities();
    }

    public Map<String, Manager> getManagers() {
        return managers;
    }

    public void generatePriorities() {
        for (LoadPriority loadPriority : LoadPriority.values()) {
            loadPriorityList.put(loadPriority, new ArrayList<>());
        }
    }

    public void registerManagers() {
        loadPriorityList.entrySet().forEach((entry) -> entry.getValue().forEach(this::loadManager));
    }

    public void loadManagers(JarFile jar) {

        jar.stream().filter((currentEntry) -> currentEntry.getName().endsWith(".class")).forEach((currentEntry) -> {
            String className = currentEntry.getName().substring(0, currentEntry.getName().length() - 6);
            className = className.replace("/", ".");
            Class clazz = null;
            try {
                clazz = getClass().getClassLoader().loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            if (!Manager.class.isAssignableFrom(clazz)) {
                return;
            }

            if (clazz == Manager.class || clazz == ModuleManager.class) {
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


            if (loadPriority == LoadPriority.NEVER) {
                return;
            }

            loadPriorityList.get(loadPriority).add(clazz);
        });

        try {
            jar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
