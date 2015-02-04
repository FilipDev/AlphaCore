/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.largeplugincore.players;

import me.pauzen.largeplugincore.listeners.ListenerImplementation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager extends ListenerImplementation {

    private static PlayerManager manager;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (getWrapper(e.getPlayer()) == null) {
            registerPlayer(e.getPlayer());
        }
    }

    public static void registerManager() {
        manager = new PlayerManager();
    }

    public static void unregisterManager() {
        manager = null;
    }

    public static PlayerManager getManager() {
        return manager;
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
