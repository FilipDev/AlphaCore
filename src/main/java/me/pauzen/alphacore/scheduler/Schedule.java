/*
 *  Created by Filip P. on 7/2/15 12:21 AM.
 */

package me.pauzen.alphacore.scheduler;

import me.pauzen.alphacore.Core;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.function.Consumer;

public final class Schedule {

    private Schedule() {
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer, long delay, int perRun) {
        new BukkitRunnable() {
            
            int index = 0;
            
            @Override
            public void run() {
                
                if (list.size() == index) {
                    cancel();
                    return;
                }

                for (int i = 0; i < perRun; i++) {
                    consumer.accept(list.get(index));
                    index++;
                }

            }
        }.runTaskTimer(Core.getCallerPlugin(), 0L, delay);
    }
    
}
