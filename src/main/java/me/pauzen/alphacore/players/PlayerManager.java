/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.loadingbar.LoadingBar;
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

    public static Collection<CorePlayer> getCorePlayers() {
        return manager.players.values();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (getWrapper(e.getPlayer()) == null) {
            registerPlayer(e.getPlayer());
            CorePlayer corePlayer = CorePlayer.get(e.getPlayer());
            corePlayer.addAttribute("loading_bar", new LoadingBar(corePlayer).display(400));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        LoadingBar loadingBar = CorePlayer.get(e.getPlayer()).getAttribute(LoadingBar.class, "loading_bar");
        if (loadingBar != null) {
            loadingBar.revert();
        }
        destroyWrapper(e.getPlayer());
    }

    public static void register() {
        manager = new PlayerManager();
    }

    public static PlayerManager getManager() {
        return manager;
    }

    private Map<UUID, CorePlayer> players = new HashMap<>();

    public void registerPlayer(Player player) {
        this.players.put(player.getUniqueId(), new CorePlayer(player));
    }

    public CorePlayer getWrapper(Player player) {
        return players.get(player.getUniqueId());
    }

    public void destroyWrapper(Player player) {
        this.players.get(player.getUniqueId()).save();
        this.players.remove(player.getUniqueId());
    }

}
