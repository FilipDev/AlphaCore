/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager extends ListenerImplementation implements ModuleManager<CorePlayer> {

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

    public CorePlayer getWrapper(Player player) {
        return players.get(player.getUniqueId());
    }

    public void registerPlayer(Player player) {
        this.players.put(player.getUniqueId(), new CorePlayer(player));
        CorePlayer.get(player).load();
    }

    public void destroyWrapper(Player player) {
        CorePlayer.get(player).unload();
        this.players.remove(player.getUniqueId());
    }

    @Override
    public void nullify() {
        getCorePlayers().forEach(player -> destroyWrapper(player.getPlayer()));
        ModuleManager.super.nullify();
    }

    @Override
    public String getName() {
        return "players";
    }

    @Override
    public Collection<CorePlayer> getModules() {
        return players.values();
    }

    @Deprecated
    @Override
    public void registerModule(CorePlayer module) {
        players.put(module.uuid(), module);
    }

    @Deprecated
    @Override
    public void unregisterModule(CorePlayer module) {
        players.remove(module.uuid());
    }
}
