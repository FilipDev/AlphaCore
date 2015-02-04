/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.permissions;

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
