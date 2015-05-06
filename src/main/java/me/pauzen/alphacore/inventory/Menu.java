/*
 *  Created by Filip P. on 2/20/15 7:01 PM.
 */

package me.pauzen.alphacore.inventory;

import me.pauzen.alphacore.inventory.elements.Element;
import me.pauzen.alphacore.inventory.elements.InteractableElement;
import me.pauzen.alphacore.inventory.elements.ToggleableElement;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import me.pauzen.alphacore.inventory.misc.ElementInteraction;
import me.pauzen.alphacore.inventory.misc.Selection;
import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.InvisibleID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Predicate;

public abstract class Menu implements ManagerModule {

    private Map<Coordinate, Element> elementMap = new HashMap<>();

    private Map<Coordinate, Predicate<InventoryClickEvent>> allowedClicking = new HashMap<>();

    private InvisibleID inventoryID;

    private Map<UUID, Inventory> openedInventories = new HashMap<>();

    private String name;
    private int    size;

    private InventoryType inventoryType;

    /**
     * Creates new Inventory with null holder, size and name as specified with the name getting an invisible unique ID appended to the end of it.
     *
     * @param name The desired name of the inventory.
     * @param rows The desired amount of rows in the inventory.
     */
    public Menu(String name, int rows) {
        this.name = name;
        inventoryType = InventoryType.CHEST;
        this.size = rows * 9;
        inventoryID = InvisibleID.generate();
        registerElements();
        fillRemaining();
        InventoryManager.getManager().registerModule(this);
    }

    public Menu(String name) {
        this.name = name;
        this.inventoryType = InventoryType.HOPPER;
        this.size = 5;
        inventoryID = InvisibleID.generate();
        registerElements();
        fillRemaining();
        InventoryManager.getManager().registerModule(this);
    }

    private static Coordinate toCoordinate(int x, int y) {
        return Coordinate.coordinate(x, y);
    }

    private static Coordinate toCoordinate(int inventorySlot) {
        return Coordinate.fromSlot(inventorySlot);
    }

    public void openInventory(Player player, Inventory inventory) {
        this.openedInventories.put(player.getUniqueId(), inventory);
    }

    public void closeInventory(Player player) {
        this.openedInventories.remove(player.getUniqueId());
    }

    public Inventory generateInventory(Player player) {

        Inventory inventory;

        if (inventoryType == InventoryType.HOPPER) {
            inventory = Bukkit.createInventory(null, inventoryType, name + inventoryID.getId());
        }
        else {
            inventory = Bukkit.createInventory(null, size, name + inventoryID.getId());
        }

        elementMap.entrySet().forEach(entry -> {

            int i = entry.getKey().toSlot();

            if (entry.getValue() instanceof ToggleableElement) {
                ToggleableElement toggleableElement = (ToggleableElement) entry.getValue();

                toggleableElement.testPredicate(CorePlayer.get(player), inventory);
            }
            inventory.setItem(i, entry.getValue().getRepresentation());
        });

        return inventory;
    }

    /**
     * Processes InventoryClickEvent and looks for any interactable elements that the player clicked.
     *
     * @param event InventoryClickEvent to process.
     */
    public final void process(InventoryClickEvent event) {

        Coordinate clicked = toCoordinate(event.getRawSlot());

        Element clickedElement = elementMap.get(clicked);

        if (clickedElement instanceof InteractableElement) {
            InteractableElement interactableElement = (InteractableElement) clickedElement;

            interactableElement.onInteract(new ElementInteraction((Player) event.getWhoClicked(), event.getInventory()), event.getAction() == InventoryAction.PICKUP_ALL ? ClickType.LEFT :
                    event.getAction() == InventoryAction.PICKUP_HALF ? ClickType.RIGHT : ClickType.OTHER);
        }
        if (!shouldAllowClick(event)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    /**
     * Returns whether the InventoryClickEvent should be cancelled or not.
     *
     * @param e The InventoryClickEvent to get the outcome of.
     * @return Whether or not to cancel the event.
     */
    public boolean shouldAllowClick(InventoryClickEvent e) {

        if (e.getRawSlot() >= size) {
            return true;
        }

        Coordinate inventoryCoordinate = toCoordinate(e.getRawSlot());

        Predicate<InventoryClickEvent> inventoryClickEventPredicate = allowedClicking.get(inventoryCoordinate);

        return inventoryClickEventPredicate != null && inventoryClickEventPredicate.test(e);
    }

    /**
     * Where to register elements and such.
     */
    public abstract void registerElements();

    /**
     * Triggered when a player opens an inventory.
     *
     * @param corePlayer Player that opened the inventory.
     */
    public void onOpen(CorePlayer corePlayer) {
    }

    /**
     * Fills unregistered element slots with blank elements.
     */
    public void fillRemaining() {
        for (int i = 0; i < size; i++) {

            Coordinate coordinate = toCoordinate(i);

            elementMap.putIfAbsent(coordinate, Element.BLANK_ELEMENT);
        }
    }

    /**
     * Shows the inventory to a player.
     *
     * @param player Player to show the inventory to.
     */
    public Inventory show(Player player) {
        Inventory inventory = generateInventory(player);
        openInventory(player, inventory);
        onOpen(CorePlayer.get(player));
        player.openInventory(inventory);
        return inventory;
    }

    /**
     * Shows the inventory to a player.
     *
     * @param corePlayer Player to show the inventory to.
     */
    public void show(CorePlayer corePlayer) {
        show(corePlayer.getPlayer());
    }

    /**
     * Updates an element in an already open inventory.
     *
     * @param coordinate Where to execute the update.
     * @param item       What to update the item to.
     */
    public void updateElement(Inventory inventory, Coordinate coordinate, ItemStack item) {
        inventory.setItem(coordinate.toSlot(), item);
    }

    /**
     * Updates an element in an already open inventory.
     *
     * @param coordinate Where to execute the update.
     */
    public void updateElement(Inventory inventory, Coordinate coordinate) {
        updateElement(inventory, coordinate, elementMap.get(coordinate).getRepresentation());
    }

    /**
     * Gets all elements surrounding a coordinate.
     *
     * @param coordinate The coordinate to get the elements around.
     * @return The found elements.
     */
    public Element[] getElementsAround(Coordinate coordinate) {
        Element[] foundElements = new Element[8];
        for (int i = 0; i < Coordinate.Direction.values().length; i++) {
            foundElements[i] = getElementAt(Coordinate.Direction.values()[i].getRelative(coordinate));
        }
        return foundElements;
    }

    /**
     * Gets all elements in a Selection.
     *
     * @return An array of elements found between the two points.
     */

    public Element[] getElementsIn(Selection selection) {

        Element[] elements = new Element[size];

        int slot = 0;

        for (Coordinate coordinate : selection.getCoordinates()) {
            elements[slot] = getElementAt(coordinate);
            slot++;
        }

        return elements;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return inventoryID.getId();
    }

    public Element getElementAt(Coordinate coordinate) {
        return elementMap.get(coordinate);
    }

    /**
     * Gets all elements in the menu.
     *
     * @return A list of all elements.
     */
    public List<Element> getElements() {

        List<Element> elements = new ArrayList<>();

        elementMap.entrySet().stream().map(Map.Entry::getValue).forEach(elements::add);

        return elements;
    }

    public Collection<Inventory> getOpen() {
        return openedInventories.values();
    }

    public Map<Coordinate, Predicate<InventoryClickEvent>> getAllowedClicking() {
        return allowedClicking;
    }

    @Override
    public void unload() {
        InventoryManager.getManager().unregisterModule(this);
    }
}
