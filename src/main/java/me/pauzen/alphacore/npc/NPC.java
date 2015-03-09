/*
 *  Created by Filip P. on 3/8/15 7:33 PM.
 */

package me.pauzen.alphacore.npc;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public abstract class NPC {
    
    private LivingEntity livingEntity;
    
    public abstract void onClick(PlayerInteractEntityEvent e);
    
    
    
}
