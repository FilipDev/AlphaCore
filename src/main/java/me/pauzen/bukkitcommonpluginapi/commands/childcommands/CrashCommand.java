package me.pauzen.bukkitcommonpluginapi.commands.childcommands;

import me.pauzen.bukkitcommonpluginapi.commands.Command;
import me.pauzen.bukkitcommonpluginapi.commands.CommandListener;
import me.pauzen.bukkitcommonpluginapi.utils.UnsafeBukkitClasses;
import me.pauzen.bukkitcommonpluginapi.utils.commonnms.EntityPlayer;
import me.pauzen.bukkitcommonpluginapi.utils.commonnms.Packet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CrashCommand extends Command {
    
    @Override
    public String getName() {
        return "crash";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener("buc.crash") {
            @Override
            public boolean canConsoleSend() {
                return true;
            }

            @Override
            public void onRun(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
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
                target.kickPlayer("You have been a bad, bad boy.");
            }
        };
    }
}
