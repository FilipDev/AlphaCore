package me.pauzen.alphacore.data;

import me.pauzen.alphacore.inventory.items.ItemBuilder;
import me.pauzen.alphacore.utils.misc.string.InvisibleEncoder;
import me.pauzen.alphacore.utils.misc.string.Properties;
import me.pauzen.alphacore.utils.misc.test.Test;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public final class ItemData {

    private static final String DELIMITER = InvisibleEncoder.encode("--");

    private ItemData() {
    }

    public static void applyData(ItemStack itemStack, String visiblePart, Map<String, String> dataMap) {

        if (visiblePart == null || visiblePart.equals("")) visiblePart = " ";

        String compiled = Properties.compileProperties((Map) dataMap);

        ItemBuilder itemBuilder = ItemBuilder.from(itemStack);
        List<String> lore = itemBuilder.getLore();

        if (lore.size() == 0) lore.add("");

        int line = -1;

        for (int i = 0; i < lore.size(); i++) {
            if (InvisibleEncoder.contains(lore.get(i), "--")) {
                line = i;
                break;
            }
        }

        String data = line == -1 ? "" : lore.get(line);

        if (line != -1) {
            String[] split = data.split(DELIMITER);

            String encodedProperties = split[1];
            String decoded = InvisibleEncoder.decode(encodedProperties);

            Map<String, String> properties = Properties.getProperties(decoded);

            properties.putAll(dataMap);

            data = split[0] + DELIMITER + InvisibleEncoder.encode(Properties.compileProperties((Map) properties));
        }
        else {
            data = visiblePart + DELIMITER + InvisibleEncoder.encode(compiled);
        }
        if (line == -1) { 
            if (lore.size() == 0) {
                line = 0;
            } else {
                line = lore.size();
                lore.add("");
            }
        }
        lore.set(line, data);
        itemBuilder.build();
    }

    public static Map<String, String> getData(ItemStack itemStack) throws NoSuchElementException {
        List<String> lore = ItemBuilder.from(itemStack).getLore();

        if (lore.size() == 0) {
            return new HashMap<>();
        }

        int line = -1;

        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(InvisibleEncoder.encode("--"))) {
                line = i;
                break;
            }
        }

        String data = line == -1 ? "" : lore.get(line);

        String[] split = data.split(InvisibleEncoder.encode("--"));

        if (!Test.args(split, 2)) {
            return new HashMap<>();
        }

        data = split[1];

        return Properties.getProperties(InvisibleEncoder.decode(data));
    }

    public static boolean hasData(ItemStack itemStack, String data) {
        return getData(itemStack).containsKey(data);
    }

    public static ItemMeta getMeta(ItemStack itemStack) {

        if (!itemStack.hasItemMeta()) {
            itemStack.setItemMeta(Bukkit.getItemFactory().getItemMeta(itemStack.getType()));
        }

        return itemStack.getItemMeta();
    }
}
