/*
 *  Created by Filip P. on 2/2/15 11:50 PM.
 */

package me.pauzen.largeplugincore.effects;

import me.pauzen.largeplugincore.listeners.ListenerImplementation;
import me.pauzen.largeplugincore.updater.UpdateEvent;
import me.pauzen.largeplugincore.updater.UpdateType;
import org.bukkit.event.EventHandler;

public class EffectUpdater extends ListenerImplementation {

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {

        }
    }
    
}
