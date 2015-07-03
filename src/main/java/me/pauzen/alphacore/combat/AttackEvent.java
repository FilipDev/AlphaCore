/*
 *  Created by Filip P. on 2/4/15 7:36 PM.
 */

package me.pauzen.alphacore.combat;

import me.pauzen.alphacore.combat.fight.Fight;
import me.pauzen.alphacore.events.CallableEvent;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

public class AttackEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();
    private AttackMethod attackMethod;
    private Player       attacker, defender;
    private EntityDamageEvent.DamageCause damageCause;
    private double                        damage;
    private PotionEffect                  potionEffect;

    private Event event;

    public AttackEvent(AttackMethod attackMethod, double damage, EntityDamageEvent.DamageCause damageCause, Player attacker, Player defender, Event event) {
        this.damageCause = damageCause;
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.attackMethod = attackMethod;
        this.event = event;

        createFight(this);
    }

    public AttackEvent(Player thrower, Player defender, PotionEffect potionEffect, Event event) {
        this.attacker = thrower;
        this.defender = defender;
        this.potionEffect = potionEffect;
        this.event = event;
        this.attackMethod = AttackMethod.POTION;
    }

    private static Fight createFight(AttackEvent attackEvent) {
        CorePlayer attacker = CorePlayer.get(attackEvent.getAttacker());
        CorePlayer defender = CorePlayer.get(attackEvent.getDefender());

        Fight fight = new Fight(attacker, defender, attackEvent, System.currentTimeMillis());

        attacker.addAttribute("fight", fight);
        defender.addAttribute("fight", fight);

        return fight;
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

    public AttackMethod getAttackMethod() {
        return attackMethod;
    }
}
