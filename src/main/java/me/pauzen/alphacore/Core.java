/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.core.managers.Manager;
import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.core.modules.Module;
import me.pauzen.alphacore.core.modules.PluginModule;
import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.listeners.ListenerRegisterer;
import me.pauzen.alphacore.players.PlayerManager;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.misc.Todo;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import me.pauzen.alphacore.utils.reflection.jar.JAREntryFile;
import me.pauzen.alphacore.utils.yaml.YamlBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
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

    private static Core              core;
    private static YamlConfiguration settings;
    private static YamlBuilder       yamlBuilder;
    private Map<String, Manager>                managers         = new HashMap<>();
    private EnumMap<LoadPriority, List<Class>>  loadPriorityList = new EnumMap<>(LoadPriority.class);
    private Map<JavaPlugin, List<PluginModule>> elements         = new HashMap<>();

    private Map<String, Class<Manager>> managerClassMap = new HashMap<>();

    public static Core getCore() {
        return core;
    }

    public static File getData() {
        return Core.getCore().getDataFolder();
    }

    public static void registerManager(Manager manager) {
        getCore().registerManager(manager.getClass());
    }

    //MANAGERS

    public static void unregisterManager(Manager manager) {
        if (manager instanceof ModuleManager) {
            ((ModuleManager) manager).unregister();
        }
        if (manager instanceof ListenerImplementation) {
            ((ListenerImplementation) manager).unload();
        }
        manager.onDisable();
        manager.nullify();
        getCore().managers.remove(manager.getName());
    }

    public static JAREntryFile getZipped(String name) {
        return new JAREntryFile(name);
    }

    @Deprecated
    public static void registerModule(JavaPlugin javaPlugin, PluginModule module) {
        getCore().getModules().putIfAbsent(javaPlugin, new ArrayList<>());
        getCore().getModules().get(javaPlugin).add(module);
    }

    public static JavaPlugin getCallerPlugin() {
        return JavaPlugin.getProvidingPlugin(ReflectionFactory.getCallerClasses()[1]);
    }

    //DYNAMIC LOADING

    //UTILITY METHODS
    public static Optional<JavaPlugin> getOwner(Object object) {
        return Optional.ofNullable(JavaPlugin.getProvidingPlugin(object.getClass()));
    }

    public static Optional<JavaPlugin> getOwner() {
        return Optional.ofNullable(JavaPlugin.getProvidingPlugin(ReflectionFactory.getCallerClasses()[1]));
    }

    public static void print(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static YamlConfiguration getSettings() {
        if (settings == null) {
            yamlBuilder = new YamlBuilder(new File(getData(), "settings.yml"));
            settings = yamlBuilder.getConfiguration();
        }
        return settings;
    }

    public Map<String, Class<Manager>> getManagerClassMap() {
        return managerClassMap;
    }

    public static void saveSettings() {
        yamlBuilder.save();
    }

    //MODULES (BACKWARDS COMPAT.)

    @Todo("Fix support for reloading")
    @Override
    public void onEnable() {
        core = this;

        persistent = new YamlBuilder(new File(getDataFolder(), "persistent.yml"));

        Bukkit.getPluginManager().registerEvents(this, this);

        generatePriorities();

        computeRegisterables();

        registerManagers();

        PremadeEffects.values();
        ListenerRegisterer.register();

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerManager.getManager().registerPlayer(player);
        }
    }
    
    private YamlBuilder persistent;
    
    public static YamlBuilder getPersistent() {
        return core.getPersistentData();
    }
    
    public YamlBuilder getPersistentData() {
        return persistent;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        getModules().getOrDefault(event.getPlugin(), new ArrayList<>()).forEach(PluginModule::unload);
        for (Manager manager : getManagers().values()) {
            if (manager instanceof ModuleManager) {
                ModuleManager moduleManager = (ModuleManager) manager;
                for (Object object : moduleManager.getModules()) {
                    Module module = (Module) object;
                    Optional<JavaPlugin> owner = Core.getOwner(module);
                    if (owner.isPresent()) {
                        JavaPlugin javaPlugin = owner.get();
                        if (javaPlugin == event.getPlugin()) {
                            module.unload();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        Set<Map.Entry<String, Manager>> entries = new HashSet<>();
        entries.addAll(managers.entrySet());
        entries.forEach(entry -> {
            Manager manager = entry.getValue();
            unregisterManager(manager);
        });
        saveSettings();
        core = null;
    }

    public JavaPlugin getOwner(Manager manager) {
        return JavaPlugin.getProvidingPlugin(manager.getClass());
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
        loadPriorityList.entrySet().forEach((entry) -> entry.getValue().forEach(this::registerManager));
    }

    public void computeRegisterables() {

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
    }

    public void registerManager(Class clazz) {
        try {
            print("Loading module " + clazz.getName() + "...");
            ReflectionFactory.getMethod(clazz, "register").invoke(null);
            Manager manager = (Manager) ReflectionFactory.getField(clazz, "manager").get(null);
            if (manager != null) {
                manager.onEnable();
                managers.put(manager.getName(), manager);
                managerClassMap.put(manager.getName(), (Class<Manager>) manager.getClass());
            }
            print(ChatColor.GREEN + "Loaded module " + clazz.getName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public Map<JavaPlugin, List<PluginModule>> getModules() {
        return elements;
    }
}
