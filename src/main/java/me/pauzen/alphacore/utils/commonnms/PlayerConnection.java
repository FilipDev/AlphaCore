/*
 *  Created by Filip P. on 2/2/15 11:12 PM.
 */

package me.pauzen.alphacore.utils.commonnms;

import me.pauzen.alphacore.utils.UnsafeBukkitClasses;
import me.pauzen.alphacore.utils.reflection.ReflectionFactory;
import net.minecraft.util.io.netty.channel.Channel;

import java.lang.reflect.InvocationTargetException;

public class PlayerConnection {

    private EntityPlayer entityPlayer;
    private Object       playerConnection;
    private Object       networkManager;

    public PlayerConnection(EntityPlayer entityPlayer, Object playerConnection) {
        this.entityPlayer = entityPlayer;
        this.playerConnection = playerConnection;
        try {
            this.networkManager = ReflectionFactory.getField(EntityPlayer.playerConnectionClass, "networkManager").get(playerConnection);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public void sendPacket(Packet packet) {
        try {
            ReflectionFactory.getMethod(EntityPlayer.playerConnectionClass, "sendPacket", UnsafeBukkitClasses.getNMSClass("Packet")).invoke(playerConnection, packet.getPacket());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets client version (4 = 1.7.2, 5 = 1.7.6+, 47 = 1.8)
     *
     * @return int version value.
     */
    public int getVersion() {
        try {
            return (Integer) ReflectionFactory.getMethod(UnsafeBukkitClasses.getNMSClass("NetworkManager"), "getVersion").invoke(networkManager);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Channel getNettyChannel() {
        try {
            return (Channel) ReflectionFactory.getField(UnsafeBukkitClasses.getNMSClass("NetworkManager"), "m").get(networkManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
