/*
 *  Created by Filip P. on 2/13/15 4:52 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.players.data.AutoSaver;
import me.pauzen.alphacore.utils.misc.Todo;

public class ListenerRegisterer {

    @Todo("Make features of AlphaCore more abstract so that a class like this is not required.")
    public static void register() {
        new DamageByEntityListener();
        new BlockListener();
        new AttackListener();
        new PlayerMoveListener();
        new EntitySpawnListener();
        new AutoSaver();
    }

}