/*
 *  Created by Filip P. on 2/27/15 11:26 PM.
 */

package me.pauzen.alphacore.tools;

import me.pauzen.alphacore.core.managers.ModuleManager;
import me.pauzen.alphacore.data.ItemData;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.tools.events.ToolRegisterEvent;
import me.pauzen.alphacore.utils.loading.LoadPriority;
import me.pauzen.alphacore.utils.loading.Priority;
import me.pauzen.alphacore.utils.reflection.Nullify;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Priority(LoadPriority.FIRST)
public class ToolManager extends ListenerImplementation implements ModuleManager<Tool> {

    @Nullify
    private static ToolManager manager;
    private Map<String, Tool> tools = new HashMap<>();

    public static ToolManager getManager() {
        return manager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getClickedBlock() == null) {
            return;
        }
        
        if (!isTool(e.getItem())) {
            return;
        }

        Map<String, String> properties = ItemData.getData(e.getItem());

        String type = properties.get("tool");

        Tool tool = tools.get(type);

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

    public boolean isTool(ItemStack itemStack) {
        return ItemData.hasData(itemStack, "tool");
    }

    public Tool registerTool(String type) {
        ToolRegisterEvent register = new ToolRegisterEvent(type);
        register.call();
        return register.getTool();
    }

    public String getItemName(ItemStack itemStack) {

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return "";
        }

        ItemMeta meta = ItemData.getMeta(itemStack);

        if (!meta.hasDisplayName()) {
            return "";
        }

        return meta.getDisplayName();
    }

    @Override
    public String getName() {
        return "tools";
    }

    @Override
    public Collection<Tool> getModules() {
        return tools.values();
    }

    @Override
    public void registerModule(Tool module) {
        tools.put(module.getType(), module);
    }

    @Override
    public void unregisterModule(Tool module) {
        tools.remove(module.getType());
    }

    public void setItemName(ItemStack itemStack, String string) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = ItemData.getMeta(itemStack);

        meta.setDisplayName(string);
        
        itemStack.setItemMeta(meta);
    }
}
