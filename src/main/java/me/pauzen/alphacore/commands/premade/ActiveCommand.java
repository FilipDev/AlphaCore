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
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.Roman;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@CommandMeta(
        value = "active",
        description = "Gets all active abilities and effects."
)
public class ActiveCommand extends Command {

    private static ErrorMessage NO_TARGET = new ErrorMessage("Must specify a target when running from console.");

    @Override
    public CommandListener defaultListener() {
        return new CommandListener() {
            @Override
            public void onRun() {

                Player target = Bukkit.getPlayerExact(modifiers.getOrDefault("target", ""));

                if (!commandSender.hasPermission("ac.active.other")) {
                    if (target != commandSender) {
                        ErrorMessage.PERMISSIONS.send(commandSender, "observing active abilities of other players");
                        return;
                    }
                }

                if (target == null) {
                    if (commandSender instanceof Player) {
                        target = (Player) commandSender;
                    }
                    else {
                        NO_TARGET.sendConsole();
                        return;
                    }
                }

                CorePlayer corePlayer = CorePlayer.get(target);

                ChatMessage.SPACER.sendRawMessage(commandSender, ChatColor.DARK_GREEN + target.getName() + "'s Active Abilities");
                ChatMessage.LINE_SPACER.sendRawMessage(commandSender);

                Set<Ability> totalAbilities = new HashSet<>();
                totalAbilities.addAll(corePlayer.getActivatedAbilities());
                totalAbilities.addAll(corePlayer.getCurrentPlace().getActiveAbilities());

                totalAbilities.stream().filter((ability) -> !ability.isInvisible()).forEach((ability) -> ChatMessage.LIST_ELEMENT.sendRawMessage(commandSender, "Ability", getString(ability, corePlayer)));

                corePlayer.getActiveEffects().stream().filter((effect) -> !effect.isInvisible()).forEach((effect) -> ChatMessage.LIST_ELEMENT.sendRawMessage(commandSender, "Effect", getString(effect, corePlayer)));
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
        stringBuilder.append(effect.getTimeLeft(corePlayer));
        stringBuilder.append("ms");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
