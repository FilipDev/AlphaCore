package me.pauzen.bukkitcommonpluginapi.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;

public final class BlockUtils {

    private BlockUtils() {
    }
    
    public static void dropBlock(Block block) {
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(new Location(block.getWorld(), 0, 386, 0), block.getType(), block.getData());
        block.setType(Material.AIR);
        fallingBlock.teleport(block.getLocation());
    }
}
