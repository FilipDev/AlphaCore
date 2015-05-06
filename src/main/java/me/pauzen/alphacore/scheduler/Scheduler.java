/*
 *  Created by Filip P. on 3/14/15 9:22 AM.
 */

package me.pauzen.alphacore.scheduler;

import me.pauzen.alphacore.core.managers.Manager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import me.pauzen.alphacore.utils.date.Date;
import me.pauzen.alphacore.utils.date.time.Time;
import me.pauzen.alphacore.utils.date.time.TimeFactory;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import org.bukkit.event.EventHandler;

import java.util.*;

@Priority(LoadPriority.LAST)
public class Scheduler extends ListenerImplementation implements Manager {

    private static Scheduler manager;
    private NavigableMap<Time, List<Runnable>> timeSchedule             = new TreeMap<>();
    private NavigableMap<Date, List<Runnable>> dateSchedule             = new TreeMap<>();
    private Map<Time, List<String>>            timeScheduleDescriptions = new HashMap<>();
    private Map<Date, List<String>>            dateScheduleDescriptions = new HashMap<>();

    public static void register() {
        manager = new Scheduler();
    }

    public static Scheduler getManager() {
        return manager;
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {
            List<Runnable> scheduled = getScheduled(TimeFactory.getCurrentAccurateTime());

            if (scheduled != null) {
                scheduled.forEach(Runnable::run);
            }

            scheduled = dateSchedule.get(Date.currentDate());

            if (scheduled != null) {
                scheduled.forEach(Runnable::run);
            }
        }
    }

    public List<Runnable> getScheduled(Time time) {
        return timeSchedule.get(time);
    }

    public void schedule(Time time, Runnable runnable) {
        schedule(time, runnable, "");
    }

    public void schedule(Time time, Runnable runnable, String description) {
        timeSchedule.putIfAbsent(time, new ArrayList<>());
        timeScheduleDescriptions.putIfAbsent(time, new ArrayList<>());
        timeSchedule.get(time).add(runnable);
        timeScheduleDescriptions.get(time).add(description);
    }

    public void schedule(Date date, Runnable runnable) {
        schedule(date, runnable, "");
    }

    public void schedule(Date date, Runnable runnable, String description) {
        dateSchedule.putIfAbsent(date, new ArrayList<>());
        dateScheduleDescriptions.putIfAbsent(date, new ArrayList<>());
        dateSchedule.get(date).add(runnable);
        dateScheduleDescriptions.get(date).add(description);
    }

    public SortedSet<Time> getScheduledTimes() {

        Set<Time> times = timeSchedule.keySet();

        SortedSet<Time> scheduled = new TreeSet<>(new Comparator<Time>() {

            @Override
            public int compare(Time o1, Time o2) {
                return (int) (o1.toMilliseconds() - o2.toMilliseconds());
            }
        });

        scheduled.addAll(times);

        for (Time time : times) {
            if (time.toMilliseconds() < TimeFactory.getCurrentAccurateTime().toMilliseconds()) {
                scheduled.remove(time);
            }
        }

        return scheduled;
    }

    @Override
    public String getName() {
        return "schedule";
    }

    @Override
    public void onDisable() {
        timeSchedule.clear();
        dateSchedule.clear();
        timeScheduleDescriptions.clear();
        dateScheduleDescriptions.clear();
    }
}
