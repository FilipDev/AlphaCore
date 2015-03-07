/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Ability extends ListenerImplementation {

    private Effect  effect;
    private String  abilityName;

    public Ability(String abilityName) {
        this.abilityName = abilityName;
        this.effect = new Effect(abilityName) {

            @Override
            public void onApply(CorePlayer cPlayer) {
                cPlayer.activateAbility(Ability.this);
            }

            @Override
            public void onRemove(CorePlayer cPlayer) {
                cPlayer.deactivateAbility(Ability.this);
            }

            @Override
            public void perSecond(CorePlayer cPlayer) {
            }
        };
    }
    
    public String getName() {
        return abilityName;
    }

    public Effect asEffect() {
        return this.effect;
    }

    public static String booleanToState(boolean toggled) {
        return toggled ? ChatColor.GREEN + "activated" : ChatColor.RED + "deactivated";
    }

    public void apply(CorePlayer corePlayer) {
        corePlayer.activateAbility(this);
    }

    public void remove(CorePlayer corePlayer) {
        corePlayer.deactivateAbility(this);
    }

    public boolean hasActivated(CorePlayer corePlayer) {
        return corePlayer.hasActivated(this);
    }

    public boolean hasActivated(Player player) {
        return hasActivated(CorePlayer.get(player));
    }

    public static void setAbilityState(Ability ability, CorePlayer corePlayer, boolean newState) {
        ChatMessage.SET.send(corePlayer, ability.getName(), Ability.booleanToState(corePlayer.setAbilityState(ability, newState)));
    }

    public static void toggleAbilityState(Ability ability, CorePlayer corePlayer) {
        ChatMessage.TOGGLED.send(corePlayer, ability.getName(), Ability.booleanToState(corePlayer.toggleAbilityState(ability)));
    }

    public void setAbilityState(CorePlayer corePlayer, boolean newState) {
        setAbilityState(this, corePlayer, newState);
    }

    public void toggleAbilityState(CorePlayer corePlayer) {
        toggleAbilityState(this, corePlayer);
    }
}
