/*
 *  Created by Filip P. on 2/2/15 11:16 PM.
 */

package me.pauzen.largeplugincore.points;

import me.pauzen.largeplugincore.listeners.ListenerImplementation;
import me.pauzen.largeplugincore.players.MyPlayer;
import me.pauzen.largeplugincore.updater.UpdateEvent;
import me.pauzen.largeplugincore.updater.UpdateType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PointDisplayer extends ListenerImplementation {

    private MyPlayer myPlayer;

    public PointDisplayer(MyPlayer myPlayer) {
        super();
        this.myPlayer = myPlayer;
        myPlayer.getPlayer().setLevel(myPlayer.getPoints().getValue());
    }

    public void update() {
        myPlayer.getPlayer().setLevel(myPlayer.getPoints().getValue());
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
