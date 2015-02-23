/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.doublejump;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.elements.Element;
import me.pauzen.alphacore.inventory.elements.InteractableElement;
import me.pauzen.alphacore.inventory.elements.ToggleableElement;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import me.pauzen.alphacore.listeners.EfficientMoveEvent;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.SoundUtils;
import me.pauzen.alphacore.utils.misc.Tuple;
import me.pauzen.alphacore.utils.reflection.Nullifiable;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class DoubleJumpListener extends ListenerImplementation implements Nullifiable {

    @Nullify
    private Set<CorePlayer> doubleJumped = new HashSet<>();

    public DoubleJumpListener() {
        super();
        menu = new InventoryMenu("Test", 27) {
            @Override
            public void registerElements() {
                
                ToggleableElement godToggled = new ToggleableElement(this, Coordinate.coordinate(0, 1), new Predicate<Tuple<CorePlayer, Inventory>>(){
                    @Override
                    public boolean test(Tuple<CorePlayer, Inventory> values) {
                        return values.getA().hasActivated(PremadeAbilities.GOD.ability());
                    }
                }) {
                    @Override
                    public void onToggle(Player player, boolean newState) {
                        PremadeAbilities.GOD.ability().setAbilityState(CorePlayer.get(player), newState);
                    }
                };
                
                setElementAt(0, 1, godToggled);
                setElementAt(0, 2, new Element(Material.LEASH));

                setElementAt(0, 0, new InteractableElement(Material.IRON_HELMET) {
                    @Override
                    public void onClick(Player clicker, ClickType clickType, Inventory inventory) {
                        godToggled.toggle(clicker, inventory);
                    }
                });
            }

            @Override
            public void onOpen(CorePlayer corePlayer) {

            }
        };
    }
    
    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {

        CorePlayer corePlayer = CorePlayer.get(e.getPlayer());

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

    private InventoryMenu menu;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDoubleJump(DoubleJumpEvent e) {

        menu.show(e.getPlayer());

        SoundUtils.playSound(e.getPlayer(), Sound.ENDERDRAGON_WINGS, 5, 4);
    }
}

