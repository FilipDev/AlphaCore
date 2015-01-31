package me.pauzen.bukkitcommonpluginapi;

import me.pauzen.bukkitcommonpluginapi.commands.CommandManager;
import me.pauzen.bukkitcommonpluginapi.listeners.ListenerImplementation;
import me.pauzen.bukkitcommonpluginapi.players.PlayerManager;
import me.pauzen.bukkitcommonpluginapi.points.PointManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    
    private static Core core;
    
    public static Core getCore() {
        return core;
    }
    
    @Override
    public void onEnable() {
        core = this;
        CommandManager.registerManager();
        PlayerManager.registerManager();
        PointManager.registerManager();
        ListenerImplementation.registerListeners();
    }
    
}
