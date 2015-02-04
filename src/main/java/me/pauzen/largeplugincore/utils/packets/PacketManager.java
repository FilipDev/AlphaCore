/*
 *  Modified PacketManager.java by Filip P. on 2/2/15 11:10 PM.
 */

package me.pauzen.largeplugincore.utils.packets;

import me.pauzen.largeplugincore.listeners.ListenerImplementation;
import me.pauzen.largeplugincore.players.PlayerManager;
import me.pauzen.largeplugincore.utils.commonnms.Packet;
import me.pauzen.largeplugincore.utils.packets.event.PacketReceiveEvent;
import me.pauzen.largeplugincore.utils.packets.event.PacketSendEvent;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Programmed by DevRo_ on 22/12/14.
 */
public class PacketManager extends ListenerImplementation {

    private static PacketManager instance;

    private List<PacketListener> listeners = new ArrayList<>(); //Should this be a SynchronizedList?

    public static PacketManager getInstance() {
        if (instance == null) {
            instance = new PacketManager();
        }

        return instance;
    }

    public void registerListener(PacketListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        inject(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        uninject(event.getPlayer());
    }

    public static void nullify() {
        instance = null;
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
        channel.pipeline().addBefore("packet_handler", "christmas", new PacketInterceptor(player));
    }

    public void uninject(Player player) {
        final Channel channel = PlayerManager.getManager().getWrapper(player).getNettyChannel();
        channel.eventLoop().submit(new Runnable() {
            @Override
            public void run() {
                channel.pipeline().remove("christmas");
            }
        });
    }
}
