/*
 *  Created by Filip P. on 4/10/15 9:45 PM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.data.trackers.Tracker;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandMeta("trackers")
public class TrackersCommand extends Command {

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener(false, "alphacore.trackers") {
            @Override
            public void onRun() {
                CorePlayer corePlayer = CorePlayer.get((Player) sender);

                for (Map.Entry<String, Tracker> trackerEntry : corePlayer.getTrackers().entrySet()) {
                    corePlayer.getPlayer().sendMessage(trackerEntry.getKey() + ": " + trackerEntry.getValue().getValue());
                }
            }
        };
    }
}
