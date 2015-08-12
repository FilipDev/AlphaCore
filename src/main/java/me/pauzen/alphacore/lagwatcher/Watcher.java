/*
 *  Created by Filip P. on 8/11/15 9:48 PM.
 */

package me.pauzen.alphacore.lagwatcher;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import org.bukkit.event.EventHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Watcher extends ListenerImplementation {

    private double averageTPS = -1;
    
    public Watcher(final int averageSize) {
        watcherThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }

                if (tickCounts.size() >= averageSize) {
                    tickCounts.remove();
                }
                tickCounts.add(tickCount);

                int totalTicks = 0;
                for (Integer count : tickCounts) {
                    totalTicks += count;
                }
                
                averageTPS = (double) totalTicks / (double) tickCounts.size();
                
                tickCount = 0;
            }
        });
        watcherThread.start();
    }

    private Queue<Integer> tickCounts = new ConcurrentLinkedDeque<>();

    private Thread watcherThread;

    private int tickCount = 0;

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        if (event.getUpdateType() == UpdateType.TICK) {
            tickCount++;
        }
    }

    public double getTicksPerSecond() {
        synchronized (this) {
            return averageTPS;
        }
    }

    private static Watcher instance;
    
    public static void register(int size) {
        instance = new Watcher(size);
    }
    
    public static Watcher getWatcher() {
        return instance;
    }
}
