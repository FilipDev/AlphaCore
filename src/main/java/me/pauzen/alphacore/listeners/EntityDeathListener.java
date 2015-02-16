/*
 *  Created by Filip P. on 2/15/15 1:51 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.DefaultTrackers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener extends ListenerImplementation {
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            CorePlayer corePlayer = CorePlayer.get(e.getEntity().getKiller());
            DefaultTrackers.KILLS.getTracker(corePlayer).addValue(1);
        }
    }
    
}
