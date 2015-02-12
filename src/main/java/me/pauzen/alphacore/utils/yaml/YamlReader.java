package me.pauzen.alphacore.utils.yaml;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public Tracker getTracker(CorePlayer corePlayer, String trackerName) {
        return new Tracker(trackerName, getInt(corePlayer.getUUID(), "trackers", trackerName));
    }

    //TODO: Add more premade getter methods for Bukkit API parts.

    private double getDouble(String... locations) {
        return this.yamlConfiguration.getDouble(combine(locations));
    }

    private int getInt(String... locations) {
        return this.yamlConfiguration.getInt(combine(locations));
    }

    private String getString(String... locations) {
        return this.yamlConfiguration.getString(combine(locations));
    }

    private String combine(String... locations) {
        StringBuilder locationBuilder = new StringBuilder();

        for (int i = 0; i < locations.length; i++) {

            locationBuilder.append(locations[i]);

            if (i != locations.length - 1) {
                locationBuilder.append(".");
            }
        }

        return locationBuilder.toString();
    }

    public List<Tracker> getTrackers(CorePlayer corePlayer) {
        Set<String> trackerNames = getYamlConfiguration().getConfigurationSection(corePlayer.getUUID() + ".trackers").getKeys(false);
        List<Tracker> trackerList = new ArrayList<>(trackerNames.size());
        for (String name : trackerNames) {
            trackerList.add(getTracker(corePlayer, name));
        }
        return trackerList;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
