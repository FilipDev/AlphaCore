package me.pauzen.largeplugincore.utils.yaml;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlBuilder {
    
    private File file;
    
    public YamlBuilder(String containingFolder, String name) {
        this.file = new File(containingFolder + File.pathSeparator + name + ".yml");
    }
    
    public YamlBuilder(File file) {
        this.file = file;
    }
    
    public YamlConfiguration getConfiguration() {
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(this.file);
    }

}
