package me.pauzen.bukkitcommonpluginapi.permissions;

import org.bukkit.entity.Player;

public enum Permission {
    
    CRASH("buc.crash"),
    ;
    
    private String permission;
    
    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
    
    public boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }
}
