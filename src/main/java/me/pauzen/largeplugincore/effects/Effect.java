/*
 *  Created by Filip P. on 2/2/15 11:29 PM.
 */

package me.pauzen.largeplugincore.effects;

import me.pauzen.largeplugincore.players.MyPlayer;

import java.util.HashMap;
import java.util.Map;

public abstract class Effect {

    private Map<MyPlayer, Long> affectedPlayers = new HashMap<>();

    public Effect() {

    }
    
    public abstract long getEffectLength();
    
    public abstract void onApply(MyPlayer myPlayer);
    
    public abstract void perSecond();

    public void apply(MyPlayer myPlayer) {
        affectedPlayers.put(myPlayer, System.currentTimeMillis());
        onApply(myPlayer);
    }
    
    public boolean isApplied(MyPlayer myPlayer) {
        return System.currentTimeMillis() - affectedPlayers.get(myPlayer) > getEffectLength();
    }

}
