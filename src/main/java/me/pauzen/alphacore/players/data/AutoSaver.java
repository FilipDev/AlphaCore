/*
 *  Created by Filip P. on 2/8/15 11:47 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class AutoSaver extends ListenerImplementation {
    
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.TEM_MINUTES) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                CorePlayer corePlayer = CorePlayer.get(player);
                
                corePlayer.save();
            }
        }
    }
    
}
