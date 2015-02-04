import org.bukkit.Location;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FallingBlockLocationChecker extends BukkitRunnable {
    
    private List<FallingBlock> fallingBlocks;
    private int dropHeight;
    
    public FallingBlockLocationChecker(List<FallingBlock> fallingBlocks, int dropHeight) {
        this.fallingBlocks = fallingBlocks;
        this.dropHeight = dropHeight;
    }

    @Override
    public void run() {
        fallingBlocks.stream().filter(fallingBlock -> fallingBlock.getLocation().getBlockY() <= dropHeight).forEach(this::generateBlock);
    }
    
    public void generateBlock(FallingBlock fallingBlock) {
        Location location = fallingBlock.getLocation();
        
        location.getWorld().getBlockAt(location).setType(fallingBlock.getMaterial());
        fallingBlock.remove();
    }
}
