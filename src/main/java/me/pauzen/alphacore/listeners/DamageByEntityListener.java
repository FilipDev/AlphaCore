/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.combat.AttackType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;

public class DamageByEntityListener extends ListenerImplementation {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (e.getDamager() instanceof Player) {
            if (playerAttack(e)) {
                e.setCancelled(true);
            }
        }

        if (e.getDamager() instanceof Arrow) {
            if (arrowAttack(e)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPotionEffect(PotionSplashEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }

        if (potionAttack(e)) {
            e.setCancelled(true);
        }
    }

    private boolean playerAttack(EntityDamageByEntityEvent e) {
        return new AttackEvent(AttackType.MELEE, e.getDamage(), e.getCause(), (Player) e.getDamager(), (Player) e.getEntity(), e).call().isCancelled();
    }

    private boolean arrowAttack(EntityDamageByEntityEvent e) {
        Arrow attackingArrow = (Arrow) e.getDamager();

        if (!(attackingArrow.getShooter() instanceof Player)) {
            return false;
        }

        return new AttackEvent(AttackType.ARROW, e.getDamage(), e.getCause(), (Player) attackingArrow.getShooter(), (Player) e.getEntity(), e).call().isCancelled();
    }

    private boolean potionAttack(PotionSplashEvent e) {
        Player thrower = (Player) e.getEntity().getShooter();

        e.getAffectedEntities().stream().filter(entity -> entity instanceof Player).forEach(receiver -> e.getPotion().getEffects().stream().forEach(potionEffect -> {
                            AttackEvent attackEvent = new AttackEvent(thrower, (Player) receiver, potionEffect, e);
                            if (attackEvent.call().isCancelled()) {
                                e.getAffectedEntities().remove(receiver);
                            }
                        }
                )
        );

        return e.getAffectedEntities().isEmpty();
    }

}
