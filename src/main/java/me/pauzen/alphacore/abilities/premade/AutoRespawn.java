/*
 *  Created by Filip P. on 2/28/15 10:28 AM.
 */

package me.pauzen.alphacore.abilities.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.combat.AutoRespawnEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class AutoRespawn extends Ability {

    public AutoRespawn() {
        super("Auto Respawn");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();

        CorePlayer corePlayer = CorePlayer.get(player);

        if (corePlayer.hasActivated(this)) {
            double health = player.getHealth();
            if (e.getFinalDamage() >= health) {
                if (!new AutoRespawnEvent(corePlayer, e.getCause()).call().isCancelled()) {
                    e.setCancelled(true);
                    corePlayer.healFully();
                }
            }
        }
    }

}
