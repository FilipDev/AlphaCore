/*
 *  Created by Filip P. on 7/2/15 5:16 PM.
 */

package me.pauzen.alphacore.commands.exceptions;

public class NoPermissionException extends CommandException {
    
    private final PermissionType permissionType;

    public NoPermissionException(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }
}
