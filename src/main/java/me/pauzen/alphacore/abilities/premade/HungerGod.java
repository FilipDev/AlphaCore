/*
 *  Created by Filip P. on 3/10/15 12:54 AM.
 */

package me.pauzen.alphacore.abilities.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerGod extends Ability {
    
    public HungerGod() {
        super("Hunger God");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        CorePlayer corePlayer = CorePlayer.get((Player) e.getEntity());

        if (corePlayer.hasActivated(this)) {
            ((Player) e.getEntity()).setSaturation(20F);
            e.setCancelled(true);
        }
    }
}
