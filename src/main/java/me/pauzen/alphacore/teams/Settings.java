/*
 *  Created by Filip P. on 5/7/15 11:03 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.core.managers.Manager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Settings {

    private final ConfigurationSection section;

    public Settings(Manager manager) {
        this(Core.getSettings(), manager);
    }

    public Settings(YamlConfiguration yamlConfiguration, Manager manager) {
        String sect = "managers." + manager.getName();
        if (!yamlConfiguration.isConfigurationSection(sect)) {
            yamlConfiguration.createSection(sect);
        }
        section = yamlConfiguration.getConfigurationSection(sect);
    }

    public void save() {
        Core.saveSettings();
    }

    public ConfigurationSection getSection() {
        return section;
    }

    public void set(String s, Object o) {
        section.set(s, o);
        save();
    }
}
