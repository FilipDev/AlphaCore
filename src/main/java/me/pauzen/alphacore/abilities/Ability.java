/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class Ability {

    private static Set<Ability> registeredAbilities = new HashSet<>();

    public static void registerAbility(Ability ability) {
        registeredAbilities.add(ability);
    }

    public static Set<Ability> getRegisteredAbilities() {
        return registeredAbilities;
    }

    private boolean isDefault;

    Ability(boolean isDefault) {
        registeredAbilities.add(this);
        this.isDefault = isDefault;
    }

    public boolean isDefault() {
        return isDefault;
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
    
}