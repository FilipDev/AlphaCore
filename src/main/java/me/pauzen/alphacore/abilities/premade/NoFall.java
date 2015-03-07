/*
 *  Created by Filip P. on 2/28/15 10:23 AM.
 */

package me.pauzen.alphacore.abilities.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFall extends Ability {
    
    public NoFall() {
        super("No Fall");
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }

            CorePlayer corePlayer = CorePlayer.get((Player) e.getEntity());
            
            if (corePlayer.hasActivated(this)) {
                e.setCancelled(true);
            }
        }
    }
}
