/*
 *  Created by Filip P. on 2/7/15 12:58 PM.
 */

package me.pauzen.alphacore.players.data;

import me.pauzen.alphacore.Core;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.GeneralUtils;
import me.pauzen.alphacore.utils.yaml.YamlBuilder;
import me.pauzen.alphacore.utils.yaml.YamlReader;
import me.pauzen.alphacore.utils.yaml.YamlWriter;

import java.io.File;

public class PlayerData {

    private YamlReader  yamlReader;
    private YamlWriter  yamlWriter;
    private YamlBuilder yamlBuilder;

    private CorePlayer corePlayer;

    public PlayerData(CorePlayer corePlayer) {
        File playerData = new File(Core.getCore().getDataFolder(), GeneralUtils.toFileName("player_data", corePlayer.getPlayer().getUniqueId().toString() + ".dat"));
        this.yamlBuilder = new YamlBuilder(playerData);
        this.yamlReader = new YamlReader(yamlBuilder.getConfiguration());
        this.yamlWriter = new YamlWriter(yamlBuilder.getConfiguration());
        this.corePlayer = corePlayer;
    }

    private void loadAll() {

    }

    public YamlReader getYamlReader() {
        return yamlReader;
    }

    public YamlWriter getYamlWriter() {
        return yamlWriter;
    }

    public YamlBuilder getYamlBuilder() {
        return yamlBuilder;
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }
}
