import org.bukkit.Location;
import org.bukkit.Material;

public class SetBlock {
    
    private Location location;
    private Material material;

    public SetBlock(Location location, Material material) {
        this.location = location;
        this.material = material;
    }

    public Location getLocation() {
        return location;
    }

    public Material getMaterial() {
        return material;
    }
}
