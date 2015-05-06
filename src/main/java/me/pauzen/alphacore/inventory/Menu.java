/*
 *  Created by Filip P. on 5/6/15 5:20 PM.
 */

package me.pauzen.alphacore.inventory;

import me.pauzen.alphacore.inventory.elements.Element;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import me.pauzen.alphacore.inventory.misc.Selection;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public interface Menu {

    public boolean shouldAllowClick(InventoryClickEvent e);

    public void process(InventoryClickEvent event);

    public Inventory show(Player player);

    public void show(CorePlayer corePlayer);

    public Element[] getElementsAround(Coordinate coordinate);

    public Element[] getElementsIn(Selection selection);

    public String getName();

    public Element getElementAt(Coordinate coordinate);

    public void setElementAt(Coordinate coordinate, Element element);

    public List<Element> getElements();

    public Coordinate getLastCoordinate();

    public Selection getMenuArea();

    public Selection getMenuBorder();


}
