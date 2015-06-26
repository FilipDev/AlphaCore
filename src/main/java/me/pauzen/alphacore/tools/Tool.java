/*
 *  Created by Filip P. on 5/5/15 10:23 PM.
 */

package me.pauzen.alphacore.tools;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.data.ItemData;
import me.pauzen.alphacore.inventory.misc.ClickType;
import me.pauzen.alphacore.utils.Interactable;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tool implements ManagerModule {

    public static final Tool EMPTY_TOOL = new Tool("");
    private final String type;
    private String description = ChatColor.BLUE + "A Very Useful Tool";
    private Interactable<PlayerInteractEvent> listener;
    private long            coolDown     = 0;
    private Map<UUID, Long> lastInteract = new HashMap<>();

    private boolean droppable = true;

    public Tool(String type) {
        this.type = type;
    }

    public Tool(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public void register(ItemStack itemStack, String description) {

        if (!ToolManager.getManager().isRealItem(itemStack)) {
            return;
        }

        if (ToolManager.getManager().isTool(itemStack)) {
            return;
        }

        ItemData.applyData(itemStack, description, new HashMap<String, String>() {{
            put("tool", type);
        }});
    }

    public void register(ItemStack itemStack) {
        register(itemStack, description);
    }

    public void register() {
        ToolManager.getManager().registerModule(this);
    }

    public boolean isDroppable() {
        return droppable;
    }

    public void setDroppable(boolean droppable) {
        this.droppable = droppable;
    }

    public long getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(long coolDown) {
        this.coolDown = coolDown;
    }

    public String getType() {
        return type;
    }

    public void onInteract(PlayerInteractEvent event, ClickType clickType) {

        if (getListener() != null) {

            if (System.currentTimeMillis() / 50 - lastInteract.getOrDefault(event.getPlayer().getUniqueId(), 0L) > coolDown) {
                lastInteract.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() / 50);
                getListener().onInteract(event, clickType);
            }
        }
    }

    public Interactable<PlayerInteractEvent> getListener() {
        return listener;
    }

    public void setListener(Interactable<PlayerInteractEvent> listener) {
        this.listener = listener;
    }

    @Override
    public void unload() {
        ToolManager.getManager().unregisterModule(this);
    }
}
