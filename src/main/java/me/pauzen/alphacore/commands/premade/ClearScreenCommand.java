/*
 *  Created by Filip P. on 2/15/15 1:37 AM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.PlayerManager;

@CommandMeta(value = "cls")
public class ClearScreenCommand extends Command {

    @Override
    public CommandListener defaultListener() {

        return new CommandListener(false, "core.cls") {
            @Override
            public void onRun() {
                PlayerManager.getCorePlayers().forEach(CorePlayer::clearChat);
            }
        };
    }
}
