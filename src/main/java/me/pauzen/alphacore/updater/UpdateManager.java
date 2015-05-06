/*
 *  Created by Filip P. on 2/2/15 11:13 PM.
 */

package me.pauzen.alphacore.updater;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.core.managers.Manager;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Bukkit;

@Priority(LoadPriority.LAST)
public class UpdateManager implements Manager {

    @Nullify
    private static UpdateManager manager;

    public UpdateManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getCore(), () -> {
            for (UpdateType updateType : UpdateType.values())
                if (updateType.elapsed())
                    new UpdateEvent(updateType).call();
        }, 1L, 1L);
    }

    public static void register() {
        manager = new UpdateManager();
    }

    public static UpdateManager getManager() {
        return manager;
    }

    @Override
    public String getName() {
        return "updater";
    }

}
