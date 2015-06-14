/*
 *  Created by Filip P. on 2/20/15 8:57 PM.
 */

package me.pauzen.alphacore.inventory.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack    itemStack;
    private ItemMeta     itemMeta;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();

    private ItemBuilder(ItemStack itemStack) {

        this.itemStack = itemStack;

        if (itemStack.hasItemMeta()) {
            this.itemMeta = itemStack.getItemMeta();
            this.lore = itemMeta.getLore() == null ? new ArrayList<>() : itemMeta.getLore();
        }
        else {
            itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
            this.lore = new ArrayList<>();
        }
    }

    private ItemBuilder(Material material) {

        this.itemStack = new ItemStack(material);

        itemMeta = Bukkit.getItemFactory().getItemMeta(material);
        this.lore = new ArrayList<>();
    }

    public static ItemBuilder from(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder from(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(int durability) {
        this.itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder name(String unformattedName, Object... varargs) {
        name(String.format(unformattedName, varargs));
        return this;
    }

    public String getName() {
        return itemMeta.getDisplayName();
    }

    public List<String> getLore() {
        if (itemMeta.getLore() == null) {
            itemMeta.setLore(new ArrayList<>());
        }
        return itemMeta.getLore();
    }

    public ItemBuilder lore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder lore(int index, String lore) {

        if (this.lore.size() > index) {
            this.lore.set(index, lore);
        }
        else {
            this.lore.add(lore);
        }

        return this;
    }

    public ItemBuilder clearLore() {
        this.lore.clear();
        return this;
    }

    public ItemMeta getMeta() {
        return this.itemMeta;
    }

    public ItemStack build() {
        this.itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        this.itemStack.addUnsafeEnchantments(enchantments);
        return itemStack;
    }
}
