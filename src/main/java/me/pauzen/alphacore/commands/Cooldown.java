/*
 *  Created by Filip P. on 4/1/15 10:05 PM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Function;

public abstract class Cooldown {

    private Function<Player, Boolean> cooldownCondition;
    
    public Cooldown(Function<Player, Boolean> cooldownCondition) {
        this.cooldownCondition = cooldownCondition;
    }

    public void initiate(Player player, long delay) {
        final long times = delay / 5;


        Runnable runnable = new BukkitRunnable() {

            long timesRan = 0;

            @Override
            public void run() {
                if (!cooldownCondition.apply(player)) {
                    Cooldown.this.cancel(player);
                    cancel();
                }
                if (timesRan == times) {
                    complete(player);
                    cancel();
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getCore(), runnable, 5, 5);
    }
    
    public abstract void cancel(Player player);
    
    public abstract void complete(Player player);

}
