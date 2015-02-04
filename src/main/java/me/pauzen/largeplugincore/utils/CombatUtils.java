/*
 *  Created by Filip P. on 2/2/15 11:11 PM.
 */

package me.pauzen.largeplugincore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;

public final class CombatUtils {

    private CombatUtils() {
    }
    
    public static double processDamage(Entity damager, Entity damagee, double damage) {

        if (damager instanceof Player) {
            PlayerAnimationEvent playerAnimationEvent = new PlayerAnimationEvent((Player) damager);
            Bukkit.getPluginManager().callEvent(playerAnimationEvent);
        }
            
        EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage);
        Bukkit.getPluginManager().callEvent(entityDamageByEntityEvent);
        
        if (entityDamageByEntityEvent.isCancelled()) {
            return 0;
        }
        
        return entityDamageByEntityEvent.getFinalDamage();
    }
    
    public static void bleedEffect(Entity entity) {
        entity.getWorld().playEffect(entity.getLocation(), org.bukkit.Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        entity.getWorld().playEffect(entity.getLocation(), org.bukkit.Effect.STEP_SOUND, Material.REDSTONE_WIRE);
    }

}
