/*
 *  Created by Filip P. on 3/22/15 9:24 PM.
 */

package me.pauzen.alphacore.blocks;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.executors.ClickExecutor;
import me.pauzen.alphacore.utils.misc.test.Test;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClickableBlock implements ManagerModule {

    private Location location;
    private long cooldown = 2000L;
    private ClickExecutor<ClickableBlock, PlayerInteractEvent> clickExecutor;
    private Map<UUID, Long> clickTimes = new HashMap<>();

    public ClickableBlock(Location location) {
        this.location = location.getBlock().getLocation();
    }

    public ClickableBlock(Location location, long cooldown) {
        this.location = location;
        this.cooldown = cooldown;
    }

    public void onClick(ClickType clickType, CorePlayer corePlayer, PlayerInteractEvent event) {
        if (Test.VALID.test(this.clickExecutor)) {
            this.clickExecutor.onClick(clickType, corePlayer, this, event);
        }
    }

    public ClickExecutor<ClickableBlock, PlayerInteractEvent> getClickExecutor() {
        return this.clickExecutor;
    }

    public void setClickExecutor(ClickExecutor<ClickableBlock, PlayerInteractEvent> clickExecutor) {
        this.clickExecutor = clickExecutor;
    }

    public Location getLocation() {
        return this.location;
    }

    public void unload() {
        ClickableBlockManager.getManager().unregisterModule(this);
    }

    public String toString() {
        return "ClickableBlock{location=" + this.location + '}';
    }

    public long getCooldown() {
        return this.cooldown;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public void addClickTime(UUID uuid) {
        this.clickTimes.put(uuid, System.currentTimeMillis());
    }

    public boolean shouldClick(UUID uuid) {
        this.clickTimes.putIfAbsent(uuid, 0L);
        return System.currentTimeMillis() - this.clickTimes.get(uuid) >= this.getCooldown();
    }

    public Block getBlock() {
        return this.location.getBlock();
    }
}
