/*
 *  Created by Filip P. on 2/15/15 12:46 AM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener extends ListenerImplementation {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        CorePlayer corePlayer = CorePlayer.get((Player) e.getEntity());

        if (corePlayer.hasActivated(PremadeAbilities.HUNGER_GOD.ability())) {
            ((Player) e.getEntity()).setSaturation(20F);
            e.setCancelled(true);
        }
    }
}
