package me.pauzen.bukkitcommonpluginapi.players;

import me.pauzen.bukkitcommonpluginapi.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements Listener {
    
    private static PlayerManager playerManager;
    
    private PlayerManager() {
        Bukkit.getPluginManager().registerEvents(this, Core.getCore());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        
    }
    
    public static void registerManager() {
        playerManager = new PlayerManager();
    }
    
    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    private Map<UUID, MyPlayer> players = new HashMap<>();

    public void registerPlayer(Player player) {
        this.players.put(player.getUniqueId(), new MyPlayer(player));
    }

    public MyPlayer getWrapper(Player player) {
        return players.get(player.getUniqueId());
    }

    public void destroyWrapper(Player player) {
        this.players.get(player.getUniqueId()).save();
        this.players.remove(player.getUniqueId());
    }

}
