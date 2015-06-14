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

    private ItemData() {
    }

    public static void applyData(ItemStack itemStack, Map<String, String> dataMap) {
        String compiled = Properties.compileProperties((Map) dataMap);

        List<String> lore = ItemBuilder.from(itemStack).getLore();

        if (lore.size() == 0) {
            lore.add("");
        }

        int line = -1;

        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(line).startsWith(InvisibleEncoder.encode("--"))) {
                line = i;
                break;
            }
        }

        String data;
        if (line == -1) {
            data = "";
        }
        else {
            data = lore.get(line);
        }

        if (InvisibleEncoder.contains(data, "--")) {
            String[] split = data.split(InvisibleEncoder.encode("--"));
            String encodedProperties = split[1];
            String decoded = InvisibleEncoder.decode(encodedProperties);

            Map<String, String> properties = Properties.getProperties(decoded);

            properties.putAll(dataMap);

            String compiledProperties = Properties.compileProperties((Map) properties);

            String string = split[0] + InvisibleEncoder.encode("--" + compiledProperties);

            ItemBuilder.from(itemStack).lore(line, string).build();
        }
        else {
            String string = InvisibleEncoder.encode("--" + compiled);
            ItemBuilder.from(itemStack).lore(line, string).build();
        }
    }

    public static Map<String, String> getData(ItemStack itemStack) throws NoSuchElementException {
        List<String> lore = ItemBuilder.from(itemStack).getLore();

        if (lore.size() == 0) {
            return new HashMap<>();
        }

        int line = -1;

        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(line).startsWith(InvisibleEncoder.encode("--"))) {
                line = i;
                break;
            }
        }

        String data;
        if (line == -1) {
            data = "";
        }
        else {
            data = lore.get(line);
        }

        String[] split = data.split(InvisibleEncoder.encode("--"));

        if (!Test.args(split, 2)) {
            return new HashMap<>();
        }

        data = split[1];

        if (data == null) {
            return new HashMap<>();
        }

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
