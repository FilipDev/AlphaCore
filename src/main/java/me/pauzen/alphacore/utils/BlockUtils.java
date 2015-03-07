/*
 *  Created by Filip P. on 2/2/15 11:11 PM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.utils.misc.math.Line;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;

import java.util.ArrayList;
import java.util.List;

public final class BlockUtils {

    private BlockUtils() {
    }

    public static void dropBlock(Block block) {
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
        block.setType(Material.AIR);
        fallingBlock.teleport(block.getLocation());
    }

    public static List<Block> getSupportingBlocks(Entity entity, double width) {

        Location location = entity.getLocation();
        location.subtract(0, 1, 0);

        List<Block> supportingBlocks = new ArrayList<>();
        Block under = location.getBlock();
        supportingBlocks.add(under);
        Line xLine = new Line(location.getX() - width / 2, location.getX() + width / 2);
        Line zLine = new Line(location.getZ() - width / 2, location.getZ() + width / 2);

        if (xLine.contains(Math.floor(location.getX()) - 0.0001)) {
            supportingBlocks.add(location.subtract(1, 0, 0).getBlock());
        }

        if (xLine.contains(Math.ceil(location.getX()) + 0.0001)) {
            supportingBlocks.add(location.subtract(-1, 0, 0).getBlock());
        }

        if (zLine.contains(Math.floor(location.getZ()) - 0.0001)) {
            supportingBlocks.add(location.subtract(0, 0, 1).getBlock());
        }

        if (zLine.contains(Math.ceil(location.getZ()) + 0.0001)) {
            supportingBlocks.add(location.subtract(0, 0, -1).getBlock());
        }

        return supportingBlocks;
    }

}
