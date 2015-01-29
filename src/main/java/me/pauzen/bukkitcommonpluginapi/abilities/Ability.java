package me.pauzen.bukkitcommonpluginapi.abilities;

import org.bukkit.ChatColor;

public enum Ability {
    
    GOD(false);
    
    private boolean isDefault;
    
    Ability(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public boolean isDefault() {
        return isDefault;
    }
    
    public static String booleanToState(boolean toggled) {
        return toggled ? ChatColor.GREEN + "activated" : ChatColor.RED + "deactivated";
    }
    
}
