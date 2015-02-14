/*
 *  Created by Filip P. on 2/12/15 5:05 PM.
 */

package me.pauzen.alphacore.listeners;

import me.pauzen.alphacore.Core;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntitySpawnListener extends ListenerImplementation {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            e.getEntity().setMetadata("spawn_reason", new FixedMetadataValue(Core.getCore(), e.getSpawnReason()));
            loggedEntities.put(e.getEntity().getUniqueId().toString(), e.getEntity());
        }
    }

    public void registerAll(World world) {
        List<LivingEntity> livingEntities = new ArrayList<>(world.getLivingEntities().size());
        livingEntities.addAll(world.getLivingEntities());
        Bukkit.getScheduler().runTaskAsynchronously(Core.getCore(), new Runnable() {
            @Override
            public void run() {
                for (LivingEntity livingEntity : livingEntities) {
                    loggedEntities.put(livingEntity.getUniqueId().toString(), livingEntity);
                }
            }
        });
    }
    
    public LivingEntity getLivingEntity(String id) {
        return loggedEntities.get(id);
    }

    private Map<String, LivingEntity> loggedEntities = new ConcurrentHashMap<>();
}
