/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class DefaultTeam extends Team {

    public DefaultTeam() {
        super("Default", ChatColor.YELLOW);
    }

    private static List<CorePlayer> emptyCorePlayerList = new ArrayList<>();

    @Override
    public List<CorePlayer> getMembers() {
        return emptyCorePlayerList;
    }
 
    @Override
    public boolean equals(Object o) {
        return (TeamManager.getManager().getDefaultTeamPreventPVP() && super.equals(o));
    }
}
