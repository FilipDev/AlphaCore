/*
 *  Created by Filip P. on 2/5/15 10:53 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class AttackListener extends ListenerImplementation {
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAttack(AttackEvent e) {
        
        CorePlayer aDamager = CorePlayer.get(e.getAttacker());
        CorePlayer aReceiver = CorePlayer.get(e.getDefender());
;
        if (aDamager.getTeam() == aReceiver.getTeam()) {
            ErrorMessage.SAME_TEAM.sendMessage(aDamager);
            e.setCancelled(true);
        }
    }
    
}
