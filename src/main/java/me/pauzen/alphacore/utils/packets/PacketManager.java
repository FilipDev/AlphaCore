/*
 *  Modified PacketManager.java by Filip P. on 2/2/15 11:10 PM.
 */

package me.pauzen.alphacore.utils.packets;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.PlayerManager;
import me.pauzen.alphacore.utils.commonnms.Packet;
import me.pauzen.alphacore.utils.packets.event.PacketReceiveEvent;
import me.pauzen.alphacore.utils.packets.event.PacketSendEvent;
import me.pauzen.alphacore.utils.reflection.Registrable;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by DevRo_ on 22/12/14.
 */
public class PacketManager extends ListenerImplementation implements Registrable {

    private static PacketManager manager;

    private List<PacketListener> listeners = new ArrayList<>(); //Should this be a SynchronizedList?

    public static void register() {
        manager = new PacketManager();
    }

    public static PacketManager getManager() {
        return manager;
    }

    public void registerListener(PacketListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        inject(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        uninject(event.getPlayer());
    }

    public boolean canIncome(Player player, Packet packet) {

        PacketReceiveEvent event = new PacketReceiveEvent(packet, player);

        for (PacketListener listener : listeners) {
            listener.onReceive(event);
        }

        return !event.isCancelled();
    }

    public boolean willSend(Player player, Packet packet) {
        PacketSendEvent event = new PacketSendEvent(packet, player);

        for (PacketListener listener : listeners) {
            listener.onSend(event);
        }

        return !event.isCancelled();
    }

    public void inject(Player player) {
        Channel channel = PlayerManager.getManager().getWrapper(player).getNettyChannel();
        channel.pipeline().addBefore("packet_handler", "alphacore", new PacketInterceptor(player));
    }

    public void uninject(Player player) {
        final Channel channel = PlayerManager.getManager().getWrapper(player).getNettyChannel();
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove("alphacore");
        });
    }
}
