/*
 *  Created by Filip P. on 2/20/15 8:35 PM.
 */

package me.pauzen.alphacore.inventory.elements;

import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.items.ItemBuilder;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ToggleableElement extends Element {

    private Coordinate inventoryCoordinate;

    public static Element ON_ELEMENT  = new Element(ItemBuilder.from(Material.INK_SACK).durability(10).name("%s%sEnabled", ChatColor.GREEN, ChatColor.BOLD).build());
    public static Element OFF_ELEMENT = new Element(ItemBuilder.from(Material.INK_SACK).durability(8).name("%s%sDisabled", ChatColor.RED, ChatColor.BOLD).build());

    private boolean currentState;

    private InventoryMenu menu;

    /**
     * @param menu         InventoryMenu instance which to apply modifications to (toggles)
     * @param coordinate   Coordinate where to modify the ItemStack.
     * @param defaultState The default state of the element.
     */
    public ToggleableElement(InventoryMenu menu, Coordinate coordinate, boolean defaultState) {
        super(defaultState ? ON_ELEMENT.getRepresentation() : OFF_ELEMENT.getRepresentation());
        currentState = defaultState;
        inventoryCoordinate = coordinate;
        this.menu = menu;
    }

    /**
     * Gets the Element which represents the on or off state.
     *
     * @param state Toggle status
     * @return Pre-made on or off element.
     */
    public Element getState(boolean state) {
        return state ? ON_ELEMENT : OFF_ELEMENT;
    }

    /**
     * Invoke this method to toggle element state
     *
     * @param player Player that toggled the element.
     */
    public void toggle(Player player) {
        currentState = !currentState;
        menu.updateElement(inventoryCoordinate, currentState ? ON_ELEMENT.getRepresentation().clone() : OFF_ELEMENT.getRepresentation().clone());
        onToggle(player, currentState);
    }

    /**
     * Called when the element is toggled to allow further processing.
     *
     * @param player   Player that toggled element state.
     * @param newState New element state.
     */
    public abstract void onToggle(Player player, boolean newState);

    public static void toggleAt(InventoryMenu menu, Coordinate coordinate, Player player) {

        Element element = menu.getElementAt(coordinate);

        if (element instanceof ToggleableElement) {
            ToggleableElement toggleable = (ToggleableElement) element;

            toggleable.toggle(player);
        }
    }
}
