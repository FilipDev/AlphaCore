/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.fake;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;

public class FakeCorePlayer extends CorePlayer {

    public FakeCorePlayer(Player player) {
        super(player);
    }

    @Override
    public void load() {
    }
}
