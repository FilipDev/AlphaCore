package me.pauzen.bukkitcommonpluginapi.fake;

import me.pauzen.bukkitcommonpluginapi.players.MyPlayer;
import org.bukkit.entity.Player;

public class FakeMyPlayer extends MyPlayer {
    
    public FakeMyPlayer(Player player) {
        super(player);
    }
    
    @Override
    public void load() {
    }
}
