/*
 *  Created by Filip P. on 2/7/15 12:58 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.GeneralUtils;
import me.pauzen.alphacore.utils.yaml.YamlConstructor;
import me.pauzen.alphacore.utils.yaml.YamlReader;
import me.pauzen.alphacore.utils.yaml.YamlWriter;

import java.io.File;

public class PlayerData {

    private YamlReader      yamlReader;
    private YamlWriter      yamlWriter;
    private YamlConstructor yamlConstructor;

    private CorePlayer corePlayer;

    public PlayerData(CorePlayer corePlayer) {
        File playerData = new File(Core.getCore().getDataFolder(), GeneralUtils.toFileName("player_data", corePlayer.getPlayer().getUniqueId().toString() + ".dat"));
        this.yamlConstructor = new YamlConstructor(playerData);
        this.yamlReader = new YamlReader(yamlConstructor.getConfiguration());
        this.yamlWriter = new YamlWriter(yamlConstructor.getConfiguration());
        this.corePlayer = corePlayer;
    }

    public YamlReader getYamlReader() {
        return yamlReader;
    }

    public YamlWriter getYamlWriter() {
        return yamlWriter;
    }

    public YamlConstructor getYamlConstructor() {
        return yamlConstructor;
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }
}
