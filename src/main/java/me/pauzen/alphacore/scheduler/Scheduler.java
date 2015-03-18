/*
 *  Created by Filip P. on 3/14/15 9:22 AM.
 */

package me.pauzen.alphacore.scheduler;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.date.time.Time;
import me.pauzen.alphacore.utils.date.time.TimeFactory;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Priority(LoadPriority.LAST)
public class Scheduler extends ListenerImplementation implements Registrable {
    
    private static Scheduler manager;
    
    public static void register() {
        manager = new Scheduler();
    }
    
    public static Scheduler getManager() {
        return manager;
    }

    private Map<Time, List<Runnable>> schedule = new HashMap<>();
    
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {
            List<Runnable> scheduled = getScheduled(TimeFactory.getCurrentAccurateTime());
            
            if (scheduled == null) {
                return;
            }

            scheduled.forEach(java.lang.Runnable::run);
            
        }
    }
    
    public List<Runnable> getScheduled(Time time) {
        return schedule.get(time);
    }
    
    public void schedule(Time time, Runnable runnable) {
        if (schedule.get(time) == null) {
            schedule.put(time, new ArrayList<>());
        }
        
        getScheduled(time).add(runnable);
    }
    
}
