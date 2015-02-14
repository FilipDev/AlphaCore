/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Ability implements Nullifiable {

    @Nullify
    private static Set<Ability> registeredAbilities;

    public static void registerAbility(Ability ability) {
        getRegisteredAbilities().add(ability);
    }

    public static Set<Ability> getRegisteredAbilities() {
        if (registeredAbilities == null) {
            registeredAbilities = new HashSet<>();
        }
        return registeredAbilities;
    }

    private boolean isDefault;
    private Effect  effect;

    public Ability(boolean isDefault) {
        this.isDefault = isDefault;
        this.effect = new Effect() {

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
        register();
    }

    private void register() {
        registerAbility(this);
    }

    public boolean isDefault() {
        return isDefault;
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

}
