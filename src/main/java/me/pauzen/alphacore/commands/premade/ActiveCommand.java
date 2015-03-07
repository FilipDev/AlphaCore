/*
 *  Created by Filip P. on 2/15/15 12:59 AM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ActiveCommand extends Command {

    @Override
    public String[] getNames() {
        return new String[]{"active", "a"};
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener() {
            @Override
            public void onRun() {
                CorePlayer corePlayer = CorePlayer.get((Player) commandSender);

                ChatMessage.SPACER.sendRawMessage(corePlayer, ChatColor.DARK_GREEN + "Active Abilities");
                ChatMessage.LINE_SPACER.sendRawMessage(corePlayer);

                Set<Ability> totalAbilities = new HashSet<>();
                totalAbilities.addAll(corePlayer.getActivatedAbilities());
                totalAbilities.addAll(corePlayer.getCurrentPlace().getActiveAbilities());

                for (Ability ability : totalAbilities) {
                    ChatMessage.LIST_ELEMENT.sendRawMessage(corePlayer, "Ability", (corePlayer.getActivatedAbilities().contains(ability) ? ChatColor.GREEN : ChatColor.YELLOW) + ability.getName());
                }

                for (Effect effect : corePlayer.getActiveEffects()) {
                    ChatMessage.LIST_ELEMENT.sendRawMessage(corePlayer, "Effect", effect.getName(), ChatColor.LIGHT_PURPLE.toString());
                }
            }
        };
    }
}
