/*
 *  Created by Filip P. on 3/14/15 1:20 AM.
 */

package me.pauzen.alphacore.abilities.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.abilities.AbilityStateChangeEvent;
import doublejump.DoubleJump;
import doublejump.DoubleJumpListener;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.event.EventHandler;

public class DoubleJumpAbility extends Ability {
    
    public DoubleJumpAbility() {
        super("Double Jump");
    
        asCommand("djump").register();
        
        new DoubleJumpListener();
    }
    
    @EventHandler
    public void onAbilityStateChange(AbilityStateChangeEvent event) {
        
        CorePlayer corePlayer = event.getPlayer();
        
        if (event.getState()) {
            corePlayer.addAttribute("double_jump", new DoubleJump(corePlayer, -1, 3));
            corePlayer.getPlayer().setAllowFlight(true);
        } else {
            corePlayer.removeAttribute("double_jump");
        }
    }
}
