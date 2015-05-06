/*
 *  Created by Filip P. on 2/4/15 7:36 PM.
 */

package me.pauzen.alphacore.combat;

import me.pauzen.alphacore.events.CallableEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

public class AttackEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();
    private AttackType attackType;
    private Player     attacker, defender;
    private EntityDamageEvent.DamageCause damageCause;
    private double                        damage;
    private PotionEffect                  potionEffect;

    private Event event;

    public AttackEvent(AttackType attackType, double damage, EntityDamageEvent.DamageCause damageCause, Player attacker, Player defender, Event event) {
        this.damageCause = damageCause;
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.attackType = attackType;
        this.event = event;
    }

    public AttackEvent(Player thrower, Player defender, PotionEffect potionEffect, Event event) {
        this.attacker = thrower;
        this.defender = defender;
        this.potionEffect = potionEffect;
        this.event = event;
        this.attackType = AttackType.POTION;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Event getEvent() {
        return event;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public double getDamage() {
        return damage;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public PotionEffect getPotionEffect() {
        return potionEffect;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}
