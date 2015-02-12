/*
 *  Created by Filip P. on 2/8/15 9:22 PM.
 */

package me.pauzen.alphacore.loadingbar;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerExpChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class LoadingBarManager extends ListenerImplementation implements Registrable {

    @Nullify
    private static LoadingBarManager manager;

    public static void register() {
        manager = new LoadingBarManager();
    }

    public static LoadingBarManager getManager() {
        return manager;
    }

    private Map<String, LoadingBar> loadingBars = new HashMap<>();

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.TICK) {
            loadingBars.entrySet().forEach(entry -> entry.getValue().update());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onXPEvent(PlayerExpChangeEvent e) {
        CorePlayer corePlayer = CorePlayer.get(e.getPlayer());

        LoadingBar loadingBar = loadingBars.get(corePlayer.getUUID());

        if (loadingBar == null) {
            return;
        }

        loadingBar.setPreviousXP(loadingBar.getPreviousXP() + e.getAmount());
    }

    public void registerBar(LoadingBar loadingBar) {
        this.loadingBars.put(loadingBar.getPlayer().getUUID(), loadingBar);
    }

    public void deregisterBar(LoadingBar loadingBar) {
        this.loadingBars.remove(loadingBar.getPlayer().getUUID());
    }

}
