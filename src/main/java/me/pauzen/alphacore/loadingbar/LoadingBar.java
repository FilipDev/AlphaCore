/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.loadingbar;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.ExperienceUtils;

public class LoadingBar {

    private float current = 0;
    private CorePlayer corePlayer;

    private int   previousLevel;
    private float previousXP;

    public LoadingBar(CorePlayer CorePlayer) {
        this.corePlayer = CorePlayer;
        previousLevel = CorePlayer.getPlayer().getLevel();
        previousXP = CorePlayer.getPlayer().getExp();
    }

    private float xpPerTick;

    private boolean displaying = false;

    public void display(int ticks) {
        int requiredXP = ExperienceUtils.getRequiredExperience(corePlayer.getPlayer().getLevel());
        xpPerTick = requiredXP / ticks;
        displaying = true;
        corePlayer.getPlayer().setExp(0);
    }

    public float getCurrent() {
        return this.current;
    }

    public void update() {
        if (displaying) {
            corePlayer.getPlayer().setExp(current = (corePlayer.getPlayer().getExp() + xpPerTick));

            if (corePlayer.getPlayer().getExpToLevel() < xpPerTick) {
                revert();
            }
        }
    }

    public void setPreviousXP(float value) {
        this.previousXP = value;
    }

    public float getPreviousXP() {
        return this.previousXP;
    }

    public void revert() {
        displaying = false;
        corePlayer.getPlayer().setExp(previousXP);
        corePlayer.getPlayer().setLevel(previousLevel);
        new LoadedEvent(corePlayer).call();
    }

    public CorePlayer getPlayer() {
        return corePlayer;
    }
}
