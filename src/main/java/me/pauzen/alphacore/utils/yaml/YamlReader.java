package me.pauzen.alphacore.utils.yaml;

import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.trackers.Tracker;
import me.pauzen.alphacore.teams.Team;
import me.pauzen.alphacore.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
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
        double x = getDouble(location, "x"), y = getDouble(location, "y"), z = getDouble(location, "z"), pitch = getDouble(location, "pitch"), yaw = getDouble(location, "yaw");
        World world = Bukkit.getWorld(yamlConfiguration.getString(location + "." + "world"));
        Location location1 = new Location(world, x, y, z);
        location1.setPitch((float) pitch);
        location1.setYaw((float) yaw);
        return location1;
    }

    public Tracker getTracker(String trackerName) {
        return new Tracker(trackerName, getInt("trackers", trackerName));
    }

    public Team getTeam(CorePlayer corePlayer) {
        return TeamManager.getManager().getTeam(getString(corePlayer.getUUID(), "team"));
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

    public List<Tracker> getTrackers() {
        ConfigurationSection configurationSection = getYamlConfiguration().getConfigurationSection("trackers");
        if (configurationSection == null) {
            return null;
        }
        Set<String> trackerNames = configurationSection.getKeys(false);
        if (trackerNames == null) {
            return null;
        }
        List<Tracker> trackerList = new ArrayList<>(trackerNames.size());
        for (String name : trackerNames) {
            trackerList.add(getTracker(name));
        }
        return trackerList;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
