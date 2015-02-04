/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.doublejump;

import me.pauzen.largeplugincore.abilities.ExampleAbilities;
import me.pauzen.largeplugincore.listeners.ListenerImplementation;
import me.pauzen.largeplugincore.players.MyPlayer;
import me.pauzen.largeplugincore.players.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashSet;
import java.util.Set;

public class DoubleJumpListener extends ListenerImplementation {

    private Set<MyPlayer> doubleJumped = new HashSet<>();
    private double multiplier;
    
    public DoubleJumpListener(double strength) {
        super();
        this.multiplier = strength;
    }

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {
        
        MyPlayer myPlayer = PlayerManager.getManager().getWrapper(e.getPlayer());

        if (myPlayer.hasActivated(ExampleAbilities.DOUBLE_JUMP.ability())) {

            if (!new DoubleJumpEvent(myPlayer).call().isCancelled()) {
                this.launchPlayer(e.getPlayer());
                doubleJumped.add(myPlayer);
                e.setCancelled(true);
                e.getPlayer().setAllowFlight(false);
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        MyPlayer myPlayer = PlayerManager.getManager().getWrapper(e.getPlayer());

        if (myPlayer.hasActivated(ExampleAbilities.DOUBLE_JUMP.ability())) {
            
            if (doubleJumped.contains(myPlayer)) {
                if (e.getPlayer().isOnGround()) {
                    doubleJumped.remove(myPlayer);
                    e.getPlayer().setAllowFlight(true);
                }
            }
        }
    }
    
    private void launchPlayer(Player player) {
        Location location = player.getLocation();
        player.setVelocity(location.getDirection().multiply(multiplier));
    }

}
