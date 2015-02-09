package me.pauzen.alphacore.utils.yaml;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.Tracker;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlWriter {
    
    private YamlConfiguration yamlConfiguration;
    
    public YamlWriter(YamlConfiguration yamlConfiguration) {
        this.yamlConfiguration = yamlConfiguration;
    }
    
    public void saveLocation(String yamlSectionLocation, Location location) {
        setString(toYamlParsable(yamlSectionLocation, "world"), location.getWorld().getName());
        setDouble(toYamlParsable(yamlSectionLocation, "x"), location.getX());
        setDouble(toYamlParsable(yamlSectionLocation, "y"), location.getY());
        setDouble(toYamlParsable(yamlSectionLocation, "z"), location.getZ());
        setInt(toYamlParsable(yamlSectionLocation, "pitch"), (int) location.getPitch());
        setInt(toYamlParsable(yamlSectionLocation, "yaw"), (int) location.getYaw());
    }
    
    public String toYamlParsable(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(".");
            }
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    } 
    
    private void setString(String location, String value) {
        this.yamlConfiguration.set(location, value);
    }
    
    private void setDouble(String location, double value) {
        this.yamlConfiguration.set(location, value);
    }  
    
    private void setInt(String location, int value) {
        this.yamlConfiguration.set(location, value);
    }
    
    public YamlConfiguration getYamlConfiguration() {
        return this.yamlConfiguration;
    }
    
    public void saveTracker(CorePlayer corePlayer, Tracker tracker) {
        setInt(combine(corePlayer.getUUID(), "trackers"), tracker.getValue());
    }

    private String combine(String... locations) {
        StringBuilder locationBuilder = new StringBuilder();
        for (int i = 0; i < locations.length; i++) {
            locationBuilder.append(locations[i]);

            if (i  != locations.length - 1) {
                locationBuilder.append(".");
            }
        }
        return locationBuilder.toString();
    }

}
