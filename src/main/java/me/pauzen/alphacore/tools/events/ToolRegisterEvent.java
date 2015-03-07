/*
 *  Created by Filip P. on 2/28/15 9:19 AM.
 */

package me.pauzen.alphacore.tools.events;

import me.pauzen.alphacore.events.CallableEvent;
import me.pauzen.alphacore.tools.Tool;
import org.bukkit.event.HandlerList;

public class ToolRegisterEvent extends CallableEvent {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    private Tool tool;
    private String id;

    public ToolRegisterEvent(String id) {
        this.id = id;
    }

    public Tool getTool() {
        return tool;
    }

    public String getId() {
        return id;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setId(String id) {
        this.id = id;
    }
}
