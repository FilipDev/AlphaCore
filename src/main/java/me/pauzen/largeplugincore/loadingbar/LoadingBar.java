/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.loadingbar;

import me.pauzen.largeplugincore.players.MyPlayer;
import me.pauzen.largeplugincore.updater.UpdateEvent;
import me.pauzen.largeplugincore.updater.UpdateType;
import me.pauzen.largeplugincore.utils.ExperienceUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class LoadingBar {
    
    private int current = 0;
    private MyPlayer myPlayer;
    
    private int previousLevel;
    private float previousXP;
    
    public LoadingBar(MyPlayer myPlayer) {
        this.myPlayer = myPlayer;
        previousLevel = myPlayer.getPlayer().getLevel();
        previousXP = myPlayer.getPlayer().getExp();
    }
    
    private float xpPerTick;
    
    private boolean displaying = false;
    
    public void display(int ticks) {
        int requiredXP = ExperienceUtils.getRequiredExperience(myPlayer.getPlayer().getLevel());
        xpPerTick = requiredXP / ticks;
        displaying = true;
        myPlayer.getPlayer().setExp(0);
    }
    
    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (displaying) {
            if (e.getUpdateType() == UpdateType.TICK) {
                myPlayer.getPlayer().setExp(myPlayer.getPlayer().getExp() + xpPerTick);
                if (myPlayer.getPlayer().getExpToLevel() < xpPerTick) {
                    revert();
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onXPEvent(PlayerExpChangeEvent e) {
        if (e.getPlayer() == myPlayer.getPlayer()) {
            previousXP += e.getAmount();
        }
    }
    
    public void revert() {
        displaying = false;
        myPlayer.getPlayer().setExp(previousXP);
        new LoadedEvent(myPlayer).call();
    }
}
