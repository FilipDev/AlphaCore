package me.pauzen.alphacore.utils.yaml;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlBuilder {

    private File              file;
    private YamlConfiguration yamlConfiguration;

    public YamlBuilder(String containingFolder, String name) {
        this.file = new File(containingFolder + File.pathSeparator + name + ".yml");
    }

    public YamlBuilder(File file) {
        this.file = file;
    }

    public YamlConfiguration getConfiguration() {
        if (this.yamlConfiguration != null) {
            return this.yamlConfiguration;
        }
        if (!this.file.exists()) {
            try {
                if (!this.file.createNewFile()) {
                    throw new IOException("Failed to create file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public File getFile() {
        return this.file;
    }

    public void save() {
        try {
            getConfiguration().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
