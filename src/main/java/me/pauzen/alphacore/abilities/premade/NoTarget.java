/*
 *  Created by Filip P. on 3/13/15 10:51 PM.
 */

package me.pauzen.alphacore.abilities.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class NoTarget extends Ability {

    public NoTarget() {
        super("No Target");

        asCommand("notarget", "core.notarget").register();
    }
    
    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {
            Player player = (Player) e.getTarget();

            CorePlayer corePlayer = CorePlayer.get(player);
            
            if (corePlayer.hasActivated(this)) {
                e.setCancelled(true);
            }
        }
    }
}
