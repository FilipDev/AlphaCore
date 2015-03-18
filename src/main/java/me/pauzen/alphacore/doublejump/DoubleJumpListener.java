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
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashSet;
import java.util.Set;

public class DoubleJumpListener extends ListenerImplementation implements Nullifiable {

    @Nullify
    private Set<CorePlayer> doubleJumped = new HashSet<>();

    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        CorePlayer corePlayer = CorePlayer.get(e.getPlayer());
        
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (corePlayer.hasActivated(PremadeAbilities.DOUBLE_JUMP.ability())) {

            if (!new DoubleJumpEvent(corePlayer).call().isCancelled()) {
                e.setCancelled(true);
                launchPlayer(e.getPlayer());
                doubleJumped.add(corePlayer);
                corePlayer.activateAbility(PremadeAbilities.NO_FALL.ability());

                if (!corePlayer.getAttribute(DoubleJump.class, "double_jump").canJump()) {
                    e.getPlayer().setAllowFlight(false);
                }
            }
        }

        if (!corePlayer.hasAttribute("double_jump")) {
            e.getPlayer().setAllowFlight(false);
            return;
        }

        if (!corePlayer.getAttribute(DoubleJump.class, "double_jump").canJump()) {
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
                    corePlayer.getAttribute(DoubleJump.class, "double_jump").resetJumps();
                    e.getPlayer().getPlayer().setAllowFlight(true);
                }
            }
        }
    }

    private boolean launchPlayer(Player player) {
        return CorePlayer.get(player).getAttribute(DoubleJump.class, "double_jump").jump();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDoubleJump(DoubleJumpEvent e) {
        SoundUtils.playSound(e.getPlayer(), Sound.ENDERDRAGON_WINGS, 5, 4);
    }
}

