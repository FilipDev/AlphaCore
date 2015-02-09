/*
 *  Modified PacketInterceptor.java by Filip P. on 2/2/15 11:05 PM.
 */

package me.pauzen.alphacore.utils.packets;

import me.pauzen.alphacore.utils.commonnms.Packet;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelPromise;
import org.bukkit.entity.Player;

/**
 * Programmed by DevRo_ on 22/12/14.
 */
public class PacketInterceptor extends ChannelDuplexHandler {

    private Player player;

    public PacketInterceptor(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (PacketManager.getInstance().willSend(player, new Packet(msg))) {
            super.write(ctx, msg, promise);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (PacketManager.getInstance().canIncome(player, new Packet(msg))) {
            super.channelRead(ctx, msg);
        }
    }


}
