/*
 *  Created by Filip P. on 2/15/15 6:40 PM.
 */

package me.pauzen.alphacore.entities;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InteractableEntityManager extends ListenerImplementation implements ModuleManager<InteractableEntity> {

    @Nullify
    private static InteractableEntityManager manager;
    private Map<String, InteractableEntity> interactableEntities = new HashMap<>();

    public static void register() {
        manager = new InteractableEntityManager();
    }

    public static InteractableEntityManager getManager() {
        return manager;
    }

    public InteractableEntity getInteractableEntity(String UUID) {
        return interactableEntities.get(UUID);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) e.getRightClicked();


            InteractableEntity interactableEntity = interactableEntities.get(livingEntity.getUniqueId().toString());

            if (interactableEntity != null) {
                interactableEntity.onClick(ClickType.RIGHT, CorePlayer.get(e.getPlayer()));
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player attacker = (Player) e.getDamager();

            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) e.getEntity();

                InteractableEntity interactableEntity = interactableEntities.get(livingEntity.getUniqueId().toString());

                if (interactableEntity != null) {
                    interactableEntity.onClick(ClickType.LEFT, CorePlayer.get(attacker));

                    e.setCancelled(true);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "interactable_entities";
    }

    @Override
    public Collection<InteractableEntity> getModules() {
        return interactableEntities.values();
    }

    @Override
    public void registerModule(InteractableEntity module) {
        interactableEntities.put(module.getEntity().getUniqueId().toString(), module);
    }

    @Override
    public void unregisterModule(InteractableEntity module) {
        interactableEntities.remove(module.getEntity().getUniqueId().toString());
    }
}
