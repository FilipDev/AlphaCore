/*
 *  Created by Filip P. on 2/27/15 11:26 PM.
 */

package me.pauzen.alphacore.tools;

import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.tools.events.ToolRegisterEvent;
import me.pauzen.alphacore.utils.GeneralUtils;
import me.pauzen.alphacore.utils.InvisibleEncoder;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Priority(LoadPriority.FIRST)
public class ToolManager extends ListenerImplementation implements Registrable {

    @Nullify
    private static ToolManager manager;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        String itemName = getItemName(e.getItem());
        if (InvisibleEncoder.contains(itemName, "-tool")) {
            String type = itemName.substring(InvisibleEncoder.indexOf(itemName, "-tool-") + "-tool-".length());

            Tool tool = tools.get(getItemName(e.getItem()));

            if (tool == Tool.EMPTY_TOOL) {
                return;
            }

            if (tool == null) {

                tool = registerTool(type);

                if (tool == null) {
                    tools.put(type, Tool.EMPTY_TOOL);
                    return;
                }
            }

            tools.put(type, tool);

            Action action = e.getAction();

            tool.onInteract(e, ClickType.fromAction(action));
            
        }
    }
    
    public Tool registerTool(String type) {
        ToolRegisterEvent register = new ToolRegisterEvent(type);
        register.call();
        return register.getTool();
    }

    private String getItemName(ItemStack itemStack) {

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return "";
        }

        GeneralUtils.getMeta(itemStack);

        if (!itemStack.getItemMeta().hasDisplayName()) {
            return "";
        }

        return itemStack.getItemMeta().getDisplayName();

    }

    private Map<String, Tool> tools = new HashMap<>();

    public static void register() {
        manager = new ToolManager();
    }

    public static ToolManager getManager() {
        return manager;
    }
}
