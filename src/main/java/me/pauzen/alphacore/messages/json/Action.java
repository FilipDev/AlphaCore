/*
 *  Created by Filip P. on 4/7/15 12:03 AM.
 */

package me.pauzen.alphacore.messages.json;

public enum Action {

    OPEN_URL("open_url"),
    SUGGEST_COMMAND("suggest_command"),
    RUN_COMMAND("run_command"),

    //HoverEvent
    SHOW_TEXT("show_text"),
    SHOW_ITEM("show_item"),
    SHOW_ENTITY("show_entity"),
    SHOW_ACHIEVEMENT("show_achievement"),

    //BOOKS
    CHANGE_PAGE("change_page");

    private String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }


}
