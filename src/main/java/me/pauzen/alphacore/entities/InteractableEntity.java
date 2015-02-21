/*
 *  Created by Filip P. on 2/15/15 6:32 PM.
 */

package me.pauzen.alphacore.entities;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.LivingEntity;

public abstract class InteractableEntity {

    public abstract void onClick(ClickType clickType, CorePlayer corePlayer);

    private LivingEntity entity;

    public InteractableEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }
}