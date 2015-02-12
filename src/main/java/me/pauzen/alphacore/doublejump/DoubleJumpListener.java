/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.doublejump;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.PlayerManager;
import me.pauzen.alphacore.utils.SoundUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashSet;
import java.util.Set;

public class DoubleJumpListener extends ListenerImplementation {

    private Set<CorePlayer> doubleJumped = new HashSet<>();
    private double multiplier;

    public DoubleJumpListener(double strength) {
        super();
        this.multiplier = strength;
    }

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        CorePlayer CorePlayer = PlayerManager.getManager().getWrapper(e.getPlayer());

        if (CorePlayer.hasActivated(PremadeAbilities.DOUBLE_JUMP.ability())) {

            if (!new DoubleJumpEvent(CorePlayer).call().isCancelled()) {
                this.launchPlayer(e.getPlayer());
                doubleJumped.add(CorePlayer);
                e.setCancelled(true);
                e.getPlayer().setAllowFlight(false);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

        CorePlayer CorePlayer = PlayerManager.getManager().getWrapper(e.getPlayer());

        if (CorePlayer.hasActivated(PremadeAbilities.DOUBLE_JUMP.ability())) {

            if (doubleJumped.contains(CorePlayer)) {
                if (e.getPlayer().isOnGround()) {
                    doubleJumped.remove(CorePlayer);
                    e.getPlayer().setAllowFlight(true);
                }
            }
        }
    }

    private void launchPlayer(Player player) {
        Location location = player.getLocation();
        player.setVelocity(location.getDirection().multiply(multiplier));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDoubleJump(DoubleJumpEvent e) {
        SoundUtils.playSound(e.getPlayer(), Sound.ENDERDRAGON_WINGS, 5, 4);
    }

}
