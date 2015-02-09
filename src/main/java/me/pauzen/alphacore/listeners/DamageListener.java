/*
 *  Created by Filip P. on 2/8/15 9:06 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener extends ListenerImplementation {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        CorePlayer corePlayer = CorePlayer.get(player);

        if (corePlayer.hasActivated(PremadeAbilities.GOD.ability())) {
            e.setCancelled(true);
            return;
        }
        
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (corePlayer.hasActivated(PremadeAbilities.NO_FALL.ability())) {
                e.setCancelled(true);
                return;
            }
        }

    }
    
}
