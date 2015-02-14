/*
 *  Created by Filip P. on 2/13/15 3:55 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatListener extends ListenerImplementation {
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChatSync(PlayerChatEvent e) {
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChatSync(AsyncPlayerChatEvent e) {
        CorePlayer cPlayer = CorePlayer.get(e.getPlayer());
        
        if (!cPlayer.hasActivated(PremadeAbilities.CHAT.ability())) {
            e.setCancelled(true);
        }
    }
}
