/*
 *  Created by Filip P. on 5/23/15 2:56 PM.
 */

package me.pauzen.alphacore.combat.fight;

import me.pauzen.alphacore.combat.AttackEvent;
import me.pauzen.alphacore.players.CorePlayer;

public class Fight {

    private CorePlayer player1, player2;

    private AttackEvent lastAttackEvent;
    private long        lastAttackTime;

    public Fight(CorePlayer player1, CorePlayer player2, AttackEvent attackEvent, long lastAttackTime) {
        this.player1 = player1;
        this.player2 = player2;
        this.lastAttackEvent = attackEvent;
        this.lastAttackTime = lastAttackTime;
    }

    public CorePlayer getPlayer1() {
        return player1;
    }

    public CorePlayer getPlayer2() {
        return player2;
    }

    public AttackEvent getLastAttackEvent() {
        return lastAttackEvent;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }


}
