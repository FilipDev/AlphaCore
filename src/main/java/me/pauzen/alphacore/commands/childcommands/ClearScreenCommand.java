/*
 *  Created by Filip P. on 2/15/15 1:37 AM.
 */

package me.pauzen.alphacore.commands.childcommands;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;

public class ClearScreenCommand extends Command {

    @Override
    public String getName() {
        return "cls";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(false, "core.cls") {
            @Override
            public void onRun() {
                CorePlayer corePlayer = CorePlayer.get((Player) commandSender);

                corePlayer.clearChat();
            }
        };
    }
}
