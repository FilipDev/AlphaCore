/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.group.Group;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.Attributable;
import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

@Todo("Load teams from file.")
public abstract class Team extends Group implements ManagerModule, Attributable {

    private String    name;
    private ChatColor chatColor;

    private Map<String, Object> attributes = new HashMap<>();
    private boolean informJoin;

    public Team(String name, ChatColor chatColor) {
        this.name = name;
        this.chatColor = chatColor;
    }

    public void addPlayer(CorePlayer corePlayer) {
        super.addPlayer(corePlayer);

        if (informJoin) {
            ChatMessage.JOINED_TEAM.send(corePlayer, this.getName());
        }
    }

    public String getName() {
        return this.name;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    //TODO
    public void save() {
    }

    public void load() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (chatColor != team.chatColor) return false;
        if (name != null ? !name.equals(team.name) : team.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (chatColor != null ? chatColor.hashCode() : 0);
        return result;
    }

    @Override
    public void unload() {
        TeamManager.getManager().unregisterModule(this);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setInformJoin(boolean flag) {
        informJoin = flag;
    }
}
