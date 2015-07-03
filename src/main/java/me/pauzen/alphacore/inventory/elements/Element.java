/*
 *  Created by Filip P. on 2/20/15 8:34 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.inventory.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class Element {

    public static final Element BLANK_ELEMENT = new Element(Material.AIR);

    private ItemStack representation;

    public Element(Material material) {
        this(new ItemStack(material));
    }

    public Element(ItemStack itemStack) {
        this.representation = itemStack;
    }

    public ItemStack getRepresentation() {
        return representation;
    }

    protected void setRepresentation(ItemStack itemStack) {
        this.representation = itemStack;
    }

    public void setName(String name) {
        ItemBuilder.from(representation).name(name).build();
    }

    public void setDescription(String... description) {
        ItemBuilder builder = ItemBuilder.from(representation);
        Collections.addAll(builder.getLore(), description);
        builder.build();
    }

}
