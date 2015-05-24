/*
 *  Created by Filip P. on 3/22/15 9:24 PM.
 */

package me.pauzen.alphacore.blocks;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class ClickableBlock implements ManagerModule {

    private Location location;
    
    private long cooldown = 2000L;

    private Map<UUID, Long> clickTimes = new HashMap<>();

    public ClickableBlock(Location location) {
        this.location = location.getBlock().getLocation();
    }

    public ClickableBlock(Location location, long cooldown) {
        this.location = location;
        this.cooldown = cooldown;
    }

    public abstract void onClick(ClickType clickType, CorePlayer corePlayer);

    public Location getLocation() {
        return location;
    }

    @Override
    public void unload() {
        ClickableBlockManager.getManager().unregisterModule(this);
    }

    @Override
    public String toString() {
        return "ClickableBlock{" +
                "location=" + location +
                '}';
    }
    
    public long getCooldown() {
        return cooldown;
    }
    
    public void addClickTime(UUID uuid) {
        clickTimes.put(uuid, System.currentTimeMillis());
    }
    
    public boolean shouldClick(UUID uuid) {
        clickTimes.putIfAbsent(uuid, 0L);
        return System.currentTimeMillis() - clickTimes.get(uuid) >= getCooldown();
    }
    
    public Block getBlock() {
        return location.getBlock();
    }
}
