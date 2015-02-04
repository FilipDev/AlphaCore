/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.listeners;

import me.pauzen.largeplugincore.abilities.Ability;
import me.pauzen.largeplugincore.players.MyPlayer;
import me.pauzen.largeplugincore.players.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener extends ListenerImplementation {
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        
        Player player = (Player) e.getEntity();
        MyPlayer myPlayer = PlayerManager.getManager().getWrapper(player);

        if (myPlayer.hasActivated(Ability.GOD)) {
            e.setCancelled(true);
        }
    }
}
