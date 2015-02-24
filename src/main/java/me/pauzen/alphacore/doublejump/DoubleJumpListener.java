/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.doublejump;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.listeners.EfficientMoveEvent;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.SoundUtils;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.GameMode;

import java.util.HashSet;
import java.util.Set;

public class DoubleJumpListener extends ListenerImplementation implements Nullifiable {

    @Nullify
    private Set<CorePlayer> doubleJumped = new HashSet<>();

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        CorePlayer corePlayer = CorePlayer.get(e.getPlayer());
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        if (corePlayer.hasActivated(PremadeAbilities.DOUBLE_JUMP.ability())) {

            if (!new DoubleJumpEvent(corePlayer).call().isCancelled()) {
                e.setCancelled(true);
                launchPlayer(e.getPlayer());
                doubleJumped.add(corePlayer);
                corePlayer.activateAbility(PremadeAbilities.NO_FALL.ability());

                if (!corePlayer.getDoubleJump().canJump()) {
                    e.getPlayer().setAllowFlight(false);
                }
            }
        }

        if (!corePlayer.getDoubleJump().canJump()) {
            e.getPlayer().setAllowFlight(false);
        }
    }

    @EventHandler
    public void onPlayerMove(EfficientMoveEvent e) {

        CorePlayer corePlayer = e.getPlayer();

        if (corePlayer.hasActivated(PremadeAbilities.DOUBLE_JUMP.ability())) {
            if (!doubleJumped.contains(corePlayer)) {
                e.getPlayer().getPlayer().setAllowFlight(true);
            }

            if (doubleJumped.contains(corePlayer)) {
                if (e.getPlayer().isOnGround()) {
                    doubleJumped.remove(corePlayer);
                    corePlayer.deactivateAbility(PremadeAbilities.NO_FALL.ability());
                    corePlayer.getDoubleJump().resetJumps();
                    e.getPlayer().getPlayer().setAllowFlight(true);
                }
            }
        }
    }

    private boolean launchPlayer(Player player) {
        return CorePlayer.get(player).getDoubleJump().jump();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDoubleJump(DoubleJumpEvent e) {
        SoundUtils.playSound(e.getPlayer(), Sound.ENDERDRAGON_WINGS, 5, 4);
    }
}

