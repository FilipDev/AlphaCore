/*
 *  Created by Filip P. on 4/6/15 10:08 PM.
 */

package me.pauzen.alphacore.messages;

import me.pauzen.alphacore.messages.json.MessagePart;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JSONMessageBuilder {

    private List<String> elements = new ArrayList<>();

    public JSONMessageBuilder add(String element) {
        elements.add(element);
        return this;
    }

    public JSONMessageBuilder add(MessagePart part) {
        elements.add(part.getWhole());
        return this;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n" +
                "  text: \"\",\n" +
                "  extra: [\n");
        StringBuilder elementsBuilder = new StringBuilder();
        for (String element : elements) {
            if (!elementsBuilder.toString().isEmpty()) {
                elementsBuilder.append(",\n");
            }

            elementsBuilder.append(element);
        }
        stringBuilder.append(elementsBuilder);
        stringBuilder.append("\n  ]");
        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }

    public void send(Player player, String... values) {
        CorePlayer.get(player).sendJSON(String.format(build(), values));
    }

}