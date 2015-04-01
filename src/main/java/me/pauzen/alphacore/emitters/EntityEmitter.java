/*
 *  Created by Filip P. on 3/21/15 4:43 PM.
 */

package me.pauzen.alphacore.emitters;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.function.Consumer;

public class EntityEmitter extends Emitter {

    private Consumer<Entity> entityConsumer;
    private EntityType       entityType;

    public EntityEmitter(Consumer<Entity> entityConsumer, EntityType entityType) {
        this.entityConsumer = entityConsumer;
        this.entityType = entityType;
    }

    @Override
    public void emit() {
        entityConsumer.accept(getLocation().getWorld().spawnEntity(getLocation(), entityType));
    }
}
