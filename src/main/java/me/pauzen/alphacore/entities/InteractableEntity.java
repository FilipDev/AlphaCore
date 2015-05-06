/*
 *  Created by Filip P. on 2/15/15 6:32 PM.
 */

package me.pauzen.alphacore.entities;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.LivingEntity;

public abstract class InteractableEntity implements ManagerModule {

    private LivingEntity entity;

    public InteractableEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public abstract void onClick(ClickType clickType, CorePlayer corePlayer);

    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public void unload() {
        InteractableEntityManager.getManager().unregisterModule(this);
    }
}