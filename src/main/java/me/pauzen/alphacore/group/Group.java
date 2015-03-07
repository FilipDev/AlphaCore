/*
 *  Created by Filip P. on 2/27/15 11:21 PM.
 */

package me.pauzen.alphacore.group;

import me.pauzen.alphacore.players.CorePlayer;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<CorePlayer> members = new ArrayList<>();

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
        this.members = null;
    }

    public List<CorePlayer> getMembers() {
        return members;
    }
}
