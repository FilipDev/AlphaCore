/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.teams;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

public class DefaultTeam extends Team {
    
    public DefaultTeam() {
        super("Default", ChatColor.YELLOW);
    }

    private static Set<CorePlayer> emptyCorePlayerSet = new HashSet<>();

    @Override
    public Set<CorePlayer> getPlayers() {
        return emptyCorePlayerSet;
    }
}
