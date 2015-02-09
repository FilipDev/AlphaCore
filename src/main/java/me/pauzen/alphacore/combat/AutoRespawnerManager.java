/*
 *  Created by Filip P. on 2/6/15 6:43 PM.
 */

package me.pauzen.alphacore.combat;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.effects.PremadeEffects;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class AutoRespawnerManager extends ListenerImplementation {
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        
        Player player = (Player) e.getEntity();

        CorePlayer corePlayer = CorePlayer.get(player);
        
        if (corePlayer.hasActivated(PremadeAbilities.AUTO_RESPAWN.ability())) {
            double health = player.getHealth();
            if (e.getFinalDamage() >= health) {
                if (!new AutoRespawnEvent(corePlayer, e.getCause()).call().isCancelled()) {
                    e.setCancelled(true);
                    corePlayer.healFully();

                    PremadeEffects.NO_SHOOT_BOW.effect().apply(corePlayer);
                }
            }
        }
    }
    
}
