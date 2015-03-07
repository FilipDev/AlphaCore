/*
 *  Created by Filip P. on 2/28/15 9:36 AM.
 */

package me.pauzen.alphacore.abilities.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class God extends Ability {
    
    public God() {
        super("God");
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        CorePlayer corePlayer = CorePlayer.get(player);

        if (corePlayer.hasActivated(this)) {
            e.setCancelled(true);
        }
    }
}
