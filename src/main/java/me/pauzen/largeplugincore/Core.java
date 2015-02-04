/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.largeplugincore;

import me.pauzen.largeplugincore.commands.CommandManager;
import me.pauzen.largeplugincore.listeners.ListenerImplementation;
import me.pauzen.largeplugincore.players.PlayerManager;
import me.pauzen.largeplugincore.points.PointManager;
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
    
    @Override
    public void onDisable() {
        core = null;
        CommandManager.unregisterManager();
        PlayerManager.unregisterManager();
        PointManager.unregisterManager();
    }
    
}
