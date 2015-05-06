/*
 *  Created by Filip P. on 2/27/15 11:21 PM.
 */

package me.pauzen.alphacore.group;

import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.messages.Message;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<CorePlayer> members    = new ArrayList<>();
    private Message          cannotJoin = new ChatMessage(ChatColor.RED + "You cannot join this group right now.");

    public void addPlayer(CorePlayer corePlayer) {
        members.add(corePlayer);
    }

    public boolean isMember(CorePlayer corePlayer) {
        return members.contains(corePlayer);
    }

    public void removePlayer(CorePlayer corePlayer) {
        members.remove(corePlayer);
    }

    public void clean() {
        this.members = new ArrayList<>();
    }

    public List<CorePlayer> getMembers() {
        return members;
    }

    public void setCannotJoin(Message cannotJoin) {
        this.cannotJoin = cannotJoin;
    }

    public void tryJoining(CorePlayer player) {
        if (checkJoinable(player)) {
            addPlayer(player);
        }
        else {
            cannotJoin.send(player);
        }
    }

    public boolean checkJoinable(CorePlayer corePlayer) {
        return !isMember(corePlayer);
    }
}
