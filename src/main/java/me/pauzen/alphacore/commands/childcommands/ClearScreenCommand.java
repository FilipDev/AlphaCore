/*
 *  Created by Filip P. on 2/15/15 1:37 AM.
 */

package me.pauzen.alphacore.commands.childcommands;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.elements.InteractableElement;
import me.pauzen.alphacore.inventory.items.ItemBuilder;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import me.pauzen.alphacore.players.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ClearScreenCommand extends Command {

    @Override
    public String getName() {
        return "cls";
    }

    @Override
    public CommandListener defaultListener() {

        InventoryMenu menu = new InventoryMenu(ChatColor.RED + "Test", 6) {
            @Override
            public void registerElements() {
                ItemStack yesItem = ItemBuilder.from(Material.STAINED_GLASS_PANE).durability(5).name(String.format("%s%sYES", ChatColor.GREEN, ChatColor.BOLD)).build();
                ItemStack noItem = ItemBuilder.from(Material.STAINED_GLASS_PANE).durability(14).name(String.format("%s%sNO", ChatColor.RED, ChatColor.BOLD)).build();
                setElementsBetween(Coordinate.coordinate(0, 2), Coordinate.coordinate(3, 5), (coordinate) -> new InteractableElement((clicker, clickType, inventory) -> clicker.sendMessage("YES"), yesItem));
                setElementsBetween(Coordinate.coordinate(5, 2), Coordinate.coordinate(8, 5), (coordinate) -> new InteractableElement((clicker, clickType, inventory) -> clicker.sendMessage("NO"), noItem));
            }
        };
        
        return new CommandListener(false, "core.cls") {
            @Override
            public void onRun() {
                PlayerManager.getCorePlayers().forEach((corePlayer) -> {
                    corePlayer.clearChat();
                    menu.show(corePlayer.getPlayer());
                });
            }
        };
    }
}
