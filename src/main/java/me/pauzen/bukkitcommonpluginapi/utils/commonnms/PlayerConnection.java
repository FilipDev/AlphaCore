package me.pauzen.bukkitcommonpluginapi.utils.commonnms;

import me.pauzen.bukkitcommonpluginapi.utils.UnsafeBukkitClasses;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PlayerConnection {
    
    private EntityPlayer entityPlayer;
    private Object playerConnection;

    public PlayerConnection(EntityPlayer entityPlayer, Object playerConnection) {
        this.entityPlayer = entityPlayer;
        this.playerConnection = playerConnection;
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    private static Method sendPacketMethod;

    public void sendPacket(Packet packet) {
        if (sendPacketMethod == null) {
            try {
                sendPacketMethod = EntityPlayer.playerConnectionClass.getMethod("sendPacket", UnsafeBukkitClasses.getNMSClass("Packet"));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        try {
            sendPacketMethod.invoke(playerConnection, packet.getPacket());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
