/*
 *  Created by Filip P. on 2/2/15 11:13 PM.
 */

package me.pauzen.alphacore.updater;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.Bukkit;

public class UpdateManager implements Registrable {

    @Nullify
    private static UpdateManager updateManager;

    public static void register() {
        updateManager = new UpdateManager();
    }

    public static UpdateManager getUpdateManager() {
        return updateManager;
    }

    public UpdateManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getCore(), () -> {
            for (UpdateType updateType : UpdateType.values())
                if (updateType.elapsed())
                    new UpdateEvent(updateType).call();
        }, 1L, 1L);
    }

}
