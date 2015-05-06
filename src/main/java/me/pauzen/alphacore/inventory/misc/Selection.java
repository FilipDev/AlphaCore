/*
 *  Created by Filip P. on 5/2/15 1:44 PM.
 */

package me.pauzen.alphacore.inventory.misc;

import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.Menu;
import me.pauzen.alphacore.inventory.elements.Element;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Selection {

    private final List<Coordinate> coordinates;

    public Selection(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public Selection(Coordinate... coordinates) {
        this.coordinates = new ArrayList<>();
        Collections.addAll(this.coordinates, coordinates);
    }

    public static Selection area(Coordinate pos1, Coordinate pos2) {
        int minX = Math.min(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int maxY = Math.max(pos1.getY(), pos2.getY());

        List<Coordinate> coordinates = new ArrayList<>((maxX - minX + 1) * (maxY - minY + 1));

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                coordinates.add(Coordinate.coordinate(x, y));
            }
        }

        return new Selection(coordinates);
    }

    public static Selection border(Coordinate pos1, Coordinate pos2) {
        int minX = Math.min(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());

        int maxX = Math.max(pos1.getX(), pos2.getX());
        int maxY = Math.max(pos1.getY(), pos2.getY());

        List<Coordinate> coordinates = new ArrayList<>((maxX - minX) * 2 + (maxY - minY) * 2);

        coordinates.addAll(area(Coordinate.coordinate(minX, minY), Coordinate.coordinate(minX, maxY)).getCoordinates().stream().collect(Collectors.toList()));

        coordinates.addAll(area(Coordinate.coordinate(maxX, minY), Coordinate.coordinate(maxX, maxY)).getCoordinates().stream().collect(Collectors.toList()));

        coordinates.addAll(area(Coordinate.coordinate(minX + 1, maxY), Coordinate.coordinate(maxX - 1, maxY)).getCoordinates().stream().collect(Collectors.toList()));

        coordinates.addAll(area(Coordinate.coordinate(minX + 1, minY), Coordinate.coordinate(maxX - 1, minY)).getCoordinates().stream().collect(Collectors.toList()));

        return new Selection(coordinates);
    }

    public void replace(Menu menu, Predicate<Element> elementPredicate, Function<Coordinate, Element> coordinateElementFunction) {
        for (Coordinate coordinate : coordinates) {
            Element element = menu.getElementAt(coordinate);
            if (elementPredicate.test(element)) {
                Element newElement = coordinateElementFunction.apply(coordinate);
                menu.setElementAt(coordinate, newElement);
            }
        }
    }

    public void fillEmpty(Menu menu, Function<Coordinate, Element> coordinateElementFunction) {
        for (Coordinate coordinate : coordinates) {
            if (menu.getElementAt(coordinate) == Element.BLANK_ELEMENT) {
                menu.setElementAt(coordinate, coordinateElementFunction.apply(coordinate));
            }
        }
    }

    public void set(Menu menu, Function<Coordinate, Element> coordinateElementFunction) {
        coordinates.forEach((coordinate) -> menu.setElementAt(coordinate, coordinateElementFunction.apply(coordinate)));
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void createClickAllowanceCondition(Menu menu, Predicate<InventoryClickEvent> predicate) {
        for (Coordinate coordinate : coordinates) {
            ((InventoryMenu) menu).getAllowedClicking().put(coordinate, predicate);
        }
    }

    public void allowClicking(Menu menu) {
        for (Coordinate coordinate : coordinates) {
            ((InventoryMenu) menu).getAllowedClicking().put(coordinate, (clickEvent) -> true);
        }
    }
}
