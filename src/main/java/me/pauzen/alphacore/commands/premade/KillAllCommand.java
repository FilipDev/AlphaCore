/*
 *  Created by Filip P. on 3/14/15 9:49 AM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.messages.ChatMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@CommandMeta(
    value = "killall",
    aliases = "butcher"
)
public class KillAllCommand extends Command {

    private static ChatMessage KILLED_ALL = new ChatMessage(ChatColor.RED + "Successfully destroyed %s entities.");

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(false, "core.killall") {
            @Override
            public void onRun() {
                Player player = (Player) commandSender;

                int count = 0;
                
                boolean any = false;
                Set<String> types = new HashSet<>();
                
                if (modifiers.get("type") != null) {
                    Collections.addAll(types, modifiers.get("type").toLowerCase().split(","));
                } else {
                    any = true;
                }

                for (Entity entity : player.getWorld().getEntities()) {

                    if (!any) {
                        if (!types.contains(entity.getType().name().toLowerCase())) {
                            continue;
                        }
                    }
                    
                    if (entity instanceof Player) {
                        continue;
                    }
                    
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;

                        if (livingEntity.getCustomName() != null) {
                            continue;
                        }
                        
                        if (livingEntity.isLeashed()) {
                            continue;
                        }
                    }
                    
                    entity.remove();
                    count++;
                }

                KILLED_ALL.send(player, String.valueOf(count));
            }
        };
    }
}
