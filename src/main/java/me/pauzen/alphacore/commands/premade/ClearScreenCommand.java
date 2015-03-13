/*
 *  Created by Filip P. on 2/15/15 1:37 AM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.inventory.InventoryMenu;
import me.pauzen.alphacore.inventory.elements.AnimatedElement;
import me.pauzen.alphacore.inventory.elements.InteractableElement;
import me.pauzen.alphacore.inventory.items.ItemBuilder;
import me.pauzen.alphacore.inventory.misc.Coordinate;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandMeta(value = "cls")
public class ClearScreenCommand extends Command {

    @Override
    public CommandListener defaultListener() {

        InventoryMenu animatedMenu = new InventoryMenu(String.format("Animated"), 4) {
            @Override
            public void registerElements() {
                ItemStack animatedItem = ItemBuilder.from(Material.STAINED_GLASS_PANE).durability(0).name(String.format("%s%sANIMATION", ChatColor.GREEN, ChatColor.BOLD)).build();
                setElementsBetween(Coordinate.coordinate(0, 0), Coordinate.coordinate(8, 3), (coordinate) -> new AnimatedElement(animatedItem, this, (menu, inventory) -> {
                    getItemAt(inventory, coordinate).setDurability(((short) ((getItemAt(inventory, coordinate).getDurability() + 1) % (short) 16)));
                }, 20L));
            }
        };
        
        InventoryMenu menu = new InventoryMenu(ChatColor.RED + "Test", 6) {
            @Override
            public void registerElements() {
                ItemStack yesItem = ItemBuilder.from(Material.STAINED_GLASS_PANE).durability(5).name(String.format("%s%sYES", ChatColor.GREEN, ChatColor.BOLD)).build();
                ItemStack noItem = ItemBuilder.from(Material.STAINED_GLASS_PANE).durability(14).name(String.format("%s%sNO", ChatColor.RED, ChatColor.BOLD)).build();
                setElementsBetween(Coordinate.coordinate(0, 2), Coordinate.coordinate(3, 5), (coordinate) -> new InteractableElement((clicker, clickType, inventory) -> {
                    animatedMenu.show(clicker);
                    clicker.sendMessage("YES");
                }, yesItem));
                setElementsBetween(Coordinate.coordinate(5, 2), Coordinate.coordinate(8, 5), (coordinate) -> new InteractableElement((clicker, clickType, inventory) -> clicker.sendMessage("NO"), noItem));
            }
        };

        return new CommandListener(false, "core.cls") {
            @Override
            public void onRun() {
                PlayerManager.getCorePlayers().forEach(CorePlayer::clearChat);
                menu.show((Player) commandSender);

                Player player = (Player) commandSender;

                int playerX = player.getLocation().getBlockX();
                int playerY = player.getLocation().getBlockY();
                int playerZ = player.getLocation().getBlockZ();
                
                for (int i = 0; i < 360; i += 360 / 360) {
                    player.getWorld().getBlockAt(new Location(player.getWorld(), playerX + Math.sin(Math.toRadians(i)) * 20, playerY, playerZ + Math.cos(Math.toRadians(i)) * 20)).setType(Material.COBBLESTONE);
                }
            }
        };
    }
}
