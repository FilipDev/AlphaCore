/*
 *  Created by Filip P. on 2/2/15 11:29 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.players.CorePlayer;

import java.util.HashMap;
import java.util.Map;

public abstract class Effect {

    private Map<CorePlayer, Long> affectedPlayers = new HashMap<>();

    private long effectLength;

    /**
     * Define amount of ticks the effect lasts.
     *
     * @param effectLength Ticks until the effect wears off after application.
     */
    public Effect(long effectLength) {
        this.effectLength = effectLength * 50;
        EffectManager.getManager().registerEffect(this);
    }
    
    public Effect() {
        this.effectLength = -1;
        EffectManager.getManager().registerEffect(this);
    }

    public long getEffectLength() {
        return this.effectLength;
    }

    public abstract void onApply(CorePlayer corePlayer);
    
    public abstract void onRemove(CorePlayer corePlayer);
    
    public abstract void perSecond(CorePlayer corePlayer);

    public void apply(CorePlayer corePlayer) {
        if (new EffectGetEvent(corePlayer, this).call().isCancelled()) {
            return;
        }
        corePlayer.activateEffect(this);
        affectedPlayers.put(corePlayer, System.currentTimeMillis());
        onApply(corePlayer);
    }

    public void remove(CorePlayer corePlayer) {
        corePlayer.deactivateEffect(this);
        affectedPlayers.remove(corePlayer);
        new EffectRemoveEvent(corePlayer, this).call();
        onRemove(corePlayer);
    }
    
    public void update() {
        for (Map.Entry<CorePlayer, Long> entry : this.affectedPlayers.entrySet()) {
            perSecond(entry.getKey());
            if (getTimeLeft(entry.getKey()) <= 0) {
                remove(entry.getKey());
            }
        }
    }
    
    public long getTimeLeft(CorePlayer corePlayer) {
        return getEffectLength() == -1 ? 1 : getEffectLength() - (System.currentTimeMillis() - affectedPlayers.get(corePlayer));
    }

}
