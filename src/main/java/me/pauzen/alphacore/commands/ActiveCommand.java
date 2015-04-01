/*
 *  Created by Filip P. on 3/28/15 7:59 PM.
 */

/*
 *  Created by Filip P. on 2/15/15 12:59 AM.
 */

package me.pauzen.alphacore.commands;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@CommandMeta(
        value = "active",
        description = "Gets all active abilities and effects."
)
public class ActiveCommand extends Command {

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

                totalAbilities.stream().filter((ability) -> !ability.isInvisible()).forEach((ability) -> ChatMessage.LIST_ELEMENT.sendRawMessage(corePlayer, "Ability", (corePlayer.getActivatedAbilities().contains(ability) ? ChatColor.GREEN : ChatColor.YELLOW) + ability.getName()));

                corePlayer.getActiveEffects().stream().filter((effect) -> !effect.isInvisible()).forEach((effect) -> ChatMessage.LIST_ELEMENT.sendRawMessage(corePlayer, "Effect", ChatColor.WHITE + effect.getName() + ChatColor.LIGHT_PURPLE + " (" + effect.getTimeLeft(corePlayer) + "ms)"));
            }
        };
    }
}
