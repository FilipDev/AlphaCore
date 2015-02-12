/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.commonnms;

import me.pauzen.alphacore.utils.UnsafeBukkitClasses;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class EntityPlayer {

    private Player           player;
    private Object           entityPlayer;
    private PlayerConnection playerConnection;

    public EntityPlayer(Player player) {
        this.player = player;
        if (entityPlayerClass == null) {
            entityPlayerClass = UnsafeBukkitClasses.getNMSClass("EntityPlayer");
            playerConnectionClass = UnsafeBukkitClasses.getNMSClass("PlayerConnection");
            craftPlayerClass = UnsafeBukkitClasses.getOBCClass("entity.CraftPlayer");
        }
        try {
            this.entityPlayer = ReflectionFactory.getMethod(craftPlayerClass, "getHandle").invoke(player);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public Object getEntityPlayer() {
        return this.entityPlayer;
    }

    protected static Class entityPlayerClass, playerConnectionClass, craftPlayerClass;

    public PlayerConnection getPlayerConnection() {
        if (playerConnection == null) {
            try {
                this.playerConnection = new PlayerConnection(this, ReflectionFactory.getField(entityPlayerClass, "playerConnection").get(entityPlayer));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this.playerConnection;
    }

}
