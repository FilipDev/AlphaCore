/*
 *  Created by Filip P. on 2/15/15 11:47 AM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.players.PlayerManager;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import org.bukkit.event.EventHandler;

public class DisplayListener extends ListenerImplementation {
    
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {
            PlayerManager.getCorePlayers().stream().filter(corePlayer -> corePlayer.getTrackerDisplayer() != null).forEach(corePlayer -> corePlayer.getTrackerDisplayer().update());
        }
    }
}
