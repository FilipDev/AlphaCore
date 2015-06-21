/*
 *  Created by Filip P. on 2/20/15 8:54 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.inventory.elements.listeners.ClickListener;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.inventory.misc.ElementInteraction;
import me.pauzen.alphacore.utils.Interactable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ClickableElement extends Element implements Interactable<ElementInteraction> {

    private ClickListener clickListener;

    public ClickableElement(ClickListener clickListener, Material material) {
        super(material);
        this.clickListener = clickListener;
    }

    public ClickableElement(ClickListener clickListener, ItemStack item) {
        super(item);
        this.clickListener = clickListener;
    }

    public ClickableElement(ItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public void onInteract(ElementInteraction value, ClickType clickType) {
        clickListener.onClick(value.getClicker(), clickType, value.getInventory(), value.getMenu());
    }
}
