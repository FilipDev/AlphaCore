import me.pauzen.alphacore.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FallingBlockDropper {
    
    private List<SetBlock> setBlockList;
    private Location orginLocation;
    
    private int dropHeight = 30;
    
    public FallingBlockDropper(Location orginLocation) {
        this.orginLocation = orginLocation;
    }

    private Map<Integer, Material> materialMap = new HashMap<>();

    {
        registerMaterial(Material.WOOD, 1);
    }

    public void registerMaterial(Material material, int value) {
        materialMap.put(value, material);
    }
    
    public Material getRegisteredMaterial(int value) {
        Material material = materialMap.get(value);
        return material == null ? Material.AIR : material;
    }

    public void readLocations(String grid) {
        setBlockList = new ArrayList<>(grid.length());
        String[] zValues = grid.split("\n");
        for (int z = 0; z < zValues.length; z++) {
            String rowString = zValues[z];
            char[] blocks = rowString.toCharArray();
            for (int x = 0; x < blocks.length; x++) {
                //There has to be a better way to do this
                //Could convert char to ascii but that would require more variables
                int blockValue = Integer.valueOf(String.valueOf(x));
                
                Material material = getRegisteredMaterial(blockValue);
                
                if (material != Material.AIR) {
                    setBlockList.add(new SetBlock(this.orginLocation.add(x, dropHeight, z), material));
                }
                
            }
        }
    }

    private List<FallingBlock> spawnedFallingBlocks;

    public void dropBlocks() {
        spawnedFallingBlocks = new ArrayList<>(setBlockList.size());
        Collections.shuffle(setBlockList);
        new BukkitRunnable() {

            int currentIteration = -1;

            @Override
            public void run() {
                
                for (int x = 0; x < 2; x++) {
                    SetBlock setBlock = setBlockList.get(currentIteration++);
                    orginLocation.getWorld().spawnFallingBlock(setBlock.getLocation(), setBlock.getMaterial(), (byte) 0);
                }
            }
        }.runTaskTimer(Core.getCore(), 10, 0);
    }

}
