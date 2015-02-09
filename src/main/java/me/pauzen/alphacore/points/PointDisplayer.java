/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.alphacore.points;

import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.updater.UpdateEvent;
import me.pauzen.alphacore.updater.UpdateType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PointDisplayer extends ListenerImplementation {

    private CorePlayer CorePlayer;

    public PointDisplayer(CorePlayer CorePlayer) {
        super();
        this.CorePlayer = CorePlayer;
        CorePlayer.getPlayer().setLevel(CorePlayer.getPoints());
    }

    public void update() {
        CorePlayer.getPlayer().setLevel(CorePlayer.getPoints());
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getUpdateType() == UpdateType.SECOND) {
            update();
        }
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        e.setAmount(0);
    }
}
