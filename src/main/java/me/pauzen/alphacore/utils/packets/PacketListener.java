/*
 *  Modified PacketListener.java by Filip P. on 2/2/15 11:05 PM.
 */

package me.pauzen.alphacore.utils.packets;


import me.pauzen.alphacore.utils.packets.event.PacketReceiveEvent;
import me.pauzen.alphacore.utils.packets.event.PacketSendEvent;

/**
 * Programmed by DevRo_ on 22/12/14.
 */
public interface PacketListener {

    public void onReceive(PacketReceiveEvent event);

    public void onSend(PacketSendEvent event);
}
