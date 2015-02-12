/*
 *  Created by Filip P. on 2/2/15 11:50 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import org.bukkit.event.EventHandler;

public class EffectUpdater extends ListenerImplementation {

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {
            EffectManager.getManager().getRegisteredEffects().forEach(Effect::update);
        }
    }

}
