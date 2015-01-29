package me.pauzen.bukkitcommonpluginapi.utils;

import org.bukkit.Bukkit;
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

}
