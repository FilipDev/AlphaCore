/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager extends ListenerImplementation implements Registrable {

    @Nullify
    private static PlayerManager manager;
    private Map<UUID, CorePlayer> players = new HashMap<>();

    public static Collection<CorePlayer> getCorePlayers() {
        return manager.players.values();
    }

    public static void register() {
        manager = new PlayerManager();
    }

    public static PlayerManager getManager() {
        return manager;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (getWrapper(e.getPlayer()) == null) {
            registerPlayer(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        destroyWrapper(e.getPlayer());
    }

    public void registerPlayer(Player player) {
        this.players.put(player.getUniqueId(), new CorePlayer(player));
        CorePlayer.get(player).load();
    }

    public CorePlayer getWrapper(Player player) {
        return players.get(player.getUniqueId());
    }

    public void destroyWrapper(Player player) {
        this.players.get(player.getUniqueId()).save();
        this.players.remove(player.getUniqueId());
    }

}
