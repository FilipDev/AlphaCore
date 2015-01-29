package me.pauzen.bukkitcommonpluginapi.utils.yaml;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlReader {
    
    private YamlConfiguration yamlConfiguration;
    
    public YamlReader(YamlConfiguration yamlConfiguration) {
        this.yamlConfiguration = yamlConfiguration;
    }
    
    public Location getLocation(String location) {
        double x = getDouble(location, "x"), y = getDouble(location, "y"), z = getDouble(location, "z");
        int pitch = getInt(location, "pitch"), yaw = getInt(location, "yaw");
        World world = Bukkit.getWorld(yamlConfiguration.getString(location + "." + "world"));
        return new Location(world, x, y, z, pitch, yaw);
    }

    //TODO: Add more premade getter methods for Bukkit API parts.
    
    private double getDouble(String location, String subLocation) {
        return this.yamlConfiguration.getDouble(location + "." + subLocation);
    }
    
    private int getInt(String location, String subLocation) {
        return this.yamlConfiguration.getInt(location + "." + subLocation);
    }
    
    private String getString(String location, String subLocation) {
        return this.yamlConfiguration.getString(location + "." + subLocation);
    }
    
}
