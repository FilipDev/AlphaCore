/*
 *  Created by Filip P. on 2/2/15 11:13 PM.
 */

package me.pauzen.alphacore.updater;

import me.pauzen.alphacore.Core;
import org.bukkit.Bukkit;

public class Updater {

    private static Updater updater;

    public static void registerUpdater() {
        updater = new Updater();
    }

    public static void unregisterUpdater() {
        updater = null;
    }

    public static Updater getUpdater() {
        return updater;
    }

    public Updater() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getCore(), new Runnable() {
            @Override
            public void run() {
            for (UpdateType updateType : UpdateType.values())
                if (updateType.elapsed())
                    Bukkit.getPluginManager().callEvent(new UpdateEvent(updateType));
            }
        }, 1L, 1L);
    }

}
