/*
 *  Created by Filip P. on 2/15/15 12:51 AM.
 */

package me.pauzen.alphacore.commands.childcommands;

import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.entity.Player;

public class HungerGodCommand extends Command {

    @Override
    public String getName() {
        return "hgod";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(false, "core.hgod") {
            @Override
            public void onRun() {
                CorePlayer corePlayer = CorePlayer.get((Player) commandSender);

                PremadeAbilities.HUNGER_GOD.ability().toggleAbilityState(corePlayer);
            }
        };
    }
}
