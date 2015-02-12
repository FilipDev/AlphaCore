/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.commands.childcommands;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.players.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GodCommand extends Command {

    @Override
    public String getName() {
        return "god";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(false, "buc.godmode") {

            @Override
            public void onRun() {
                Player target = args.length == 0 ? (Player) commandSender : Bukkit.getPlayer(args[0]);
                CorePlayer myTargetPlayer = PlayerManager.getManager().getWrapper(target);
                if (modifiers.containsKey("set")) {
                    boolean setState = Boolean.getBoolean(modifiers.get("set"));
                    setGod(myTargetPlayer, setState);
                    return;
                }
                toggleGod(myTargetPlayer);
            }
        };
    }

    private void setGod(CorePlayer CorePlayer, boolean newState) {
        ChatMessage.SET.sendMessage(CorePlayer, Ability.booleanToState(CorePlayer.setAbilityState(PremadeAbilities.GOD.ability(), newState)));
    }

    private void toggleGod(CorePlayer CorePlayer) {
        ChatMessage.TOGGLED.sendMessage(CorePlayer, Ability.booleanToState(CorePlayer.toggleAbilityState(PremadeAbilities.GOD.ability())));
    }
}
