/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.fake;

import me.pauzen.largeplugincore.players.MyPlayer;
import org.bukkit.entity.Player;

public class FakeMyPlayer extends MyPlayer {
    
    public FakeMyPlayer(Player player) {
        super(player);
    }
    
    @Override
    public void load() {
    }
}
