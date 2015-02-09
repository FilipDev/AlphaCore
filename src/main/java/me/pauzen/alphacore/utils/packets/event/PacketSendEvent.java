/*
 *  Modified PacketSendEvent.java by Filip P. on 2/2/15 11:05 PM.
 */

package me.pauzen.alphacore.utils.packets.event;

import me.pauzen.alphacore.utils.commonnms.Packet;
import org.bukkit.entity.Player;

/**
 * Programmed by DevRo_ on 22/12/14.
 */

public class PacketSendEvent {

    private Packet packet;
    private Player player;
    private boolean cancelled = false;

    public PacketSendEvent(Packet packet, Player player) {
        this.packet = packet;
        this.player = player;
    }

    public Packet getPacket() {
        return packet;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
