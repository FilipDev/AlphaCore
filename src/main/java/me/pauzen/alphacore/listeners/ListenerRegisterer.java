/*
 *  Created by Filip P. on 2/13/15 4:52 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.players.data.AutoSaver;

public class ListenerRegisterer {

    public static void register() {
        new DamageByEntityListener();
        new PlayerMoveListener();
        new EntitySpawnListener();
        new AutoSaver();
    }

}