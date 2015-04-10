/*
 *  Created by Filip P. on 3/28/15 7:59 PM.
 */

/*
 *  Created by Filip P. on 2/15/15 12:59 AM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.Roman;
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

                totalAbilities.stream().filter((ability) -> !ability.isInvisible()).forEach((ability) -> ChatMessage.LIST_ELEMENT.sendRawMessage(corePlayer, "Ability", getString(ability, corePlayer)));

                corePlayer.getActiveEffects().stream().filter((effect) -> !effect.isInvisible()).forEach((effect) -> ChatMessage.LIST_ELEMENT.sendRawMessage(corePlayer, "Effect", getString(effect, corePlayer)));
            }
        };
    }
    
    private String getString(Ability ability, CorePlayer corePlayer) {
        ChatColor color = corePlayer.getActivatedAbilities().contains(ability) ? ChatColor.GREEN : ChatColor.YELLOW;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(color);
        stringBuilder.append(ability.getName());
        stringBuilder.append(" ");
        int level = corePlayer.getLevel(ability);
        if (level != 1) {
            stringBuilder.append(Roman.toRoman(level));
        }
        return stringBuilder.toString();
    }

    private String getString(Effect effect, CorePlayer corePlayer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ChatColor.WHITE);
        stringBuilder.append(effect.getName());
        stringBuilder.append(" ");
        int level = corePlayer.getLevel(effect);
        if (level != 1) {
            stringBuilder.append(Roman.toRoman(level));
        }
        stringBuilder.append(" ");
        stringBuilder.append(ChatColor.LIGHT_PURPLE);
        stringBuilder.append("(");
        stringBuilder.append(effect.getTimeLeft(corePlayer)) ;
        stringBuilder.append("ms");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
