/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore;

import me.pauzen.alphacore.commands.CommandManager;
import me.pauzen.alphacore.effects.EffectManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.loadingbar.LoadingBarManager;
import me.pauzen.alphacore.players.PlayerManager;
import me.pauzen.alphacore.points.PointManager;
import me.pauzen.alphacore.teams.TeamManager;
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
        EffectManager.registerManager();
        TeamManager.registerManager();
        LoadingBarManager.registerManager();
    }
    
    @Override
    public void onDisable() {
        core = null;
        CommandManager.unregisterManager();
        PlayerManager.unregisterManager();
        PointManager.unregisterManager();
        EffectManager.unregisterManager();
        TeamManager.unregisterManager();
        LoadingBarManager.unregisterManager();
    }
    
}
