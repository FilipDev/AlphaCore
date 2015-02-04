/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.commands.childcommands;

import me.pauzen.largeplugincore.abilities.Ability;
import me.pauzen.largeplugincore.commands.Command;
import me.pauzen.largeplugincore.commands.CommandListener;
import me.pauzen.largeplugincore.events.ToggleGodmodeEvent;
import me.pauzen.largeplugincore.messages.ChatMessage;
import me.pauzen.largeplugincore.players.MyPlayer;
import me.pauzen.largeplugincore.players.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class GodCommand extends Command {
    
    @Override
    public String getName() {
        return "god";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener("buc.godmode") {
            @Override
            public boolean canConsoleSend() {
                return false;
            }

            @Override
            public void onRun(CommandSender commandSender, String[] args, Map<String, String> modifiers) {
                Player target = args.length == 0 ? (Player) commandSender : Bukkit.getPlayer(args[0]);
                MyPlayer myTargetPlayer = PlayerManager.getManager().getWrapper(target);
                if (modifiers.containsKey("set")) {
                    boolean setState = Boolean.getBoolean(modifiers.get("set"));
                    setGod(myTargetPlayer, setState);
                    return;
                }
                toggleGod(myTargetPlayer);
            }
        };
    }

    private void setGod(MyPlayer myPlayer, boolean newState) {
        if (!new ToggleGodmodeEvent(myPlayer, newState).call().isCancelled()) {
            ChatMessage.SET.sendMessage(myPlayer, Ability.booleanToState(myPlayer.setAbilityState(Ability.GOD, newState)));
        }
    }
    
    private void toggleGod(MyPlayer myPlayer) {
        if (!new ToggleGodmodeEvent(myPlayer, !myPlayer.hasActivated(Ability.GOD)).call().isCancelled()) {
            ChatMessage.TOGGLED.sendMessage(myPlayer, Ability.booleanToState(myPlayer.toggleAbilityState(Ability.GOD)));
        }
    }
}
