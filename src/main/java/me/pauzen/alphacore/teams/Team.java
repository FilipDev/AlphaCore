/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.group.Group;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.Todo;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

@Todo("Load teams from file.")
public abstract class Team extends Group {

    private String    name;
    private ChatColor chatColor;

    public Team(String name, ChatColor chatColor) {
        this.name = name;
        this.chatColor = chatColor;
    }

    public Set<CorePlayer> getAND(CorePlayer... corePlayers) {
        Set<CorePlayer> containing = new HashSet<>();
        for (CorePlayer corePlayer : corePlayers) {
            if (isMember(corePlayer)) {
                containing.add(corePlayer);
            }
        }

        return containing;
    }

    public void addPlayer(CorePlayer corePlayer) {
        super.addPlayer(corePlayer);
        ChatMessage.JOINED_TEAM.send(corePlayer, this.getName());
    }

    public String getName() {
        return this.name;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    public void save() {
        //TODO: Add save functionality
    }

    public void load() {
        //TODO: Add load functionality
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
}
