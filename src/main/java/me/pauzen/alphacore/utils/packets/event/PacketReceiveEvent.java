/*
 *  Modified PacketReceiveEvent.java by Filip P. on 2/2/15 11:05 PM.
 */

package me.pauzen.alphacore.utils.packets.event;

import me.pauzen.alphacore.utils.commonnms.Packet;
import org.bukkit.entity.Player;

/**
 * Programmed by DevRo_ on 22/12/14.
 */
public class PacketReceiveEvent {

    private Packet packet;
    private Player player;
    private boolean cancelled = false;

    public PacketReceiveEvent(Packet packet, Player player) {
        this.packet = packet;
        this.player = player;
    }

    public Packet getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
