/*
 *  Created by Filip P. on 3/28/15 7:59 PM.
 */

/*
 *  Created by Filip P. on 2/15/15 12:59 AM.
 */

package me.pauzen.alphacore.commands.premade;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.effects.AppliedEffect;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.effects.Property;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.messages.ErrorMessage;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.utils.misc.string.Roman;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@CommandMeta(
        value = "active",
        description = "Gets all active abilities and effects."
)
public class ActiveCommand extends Command {

    private static ErrorMessage NO_TARGET = new ErrorMessage("Must specify a target when running from console.");

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener((CommandSender sender, Map<String, String> modifiers, String[] args) -> {
            Player target = Bukkit.getPlayerExact(modifiers.getOrDefault("target", ""));

            if (!sender.hasPermission("alphacore.active.other")) {
                if (target != sender) {
                    ErrorMessage.PERMISSIONS.send(sender, "observing active abilities of other players");
                    return;
                }
            }

            if (target == null) {
                if (sender instanceof Player) {
                    target = (Player) sender;
                }
                else {
                    NO_TARGET.sendConsole();
                    return;
                }
            }

            CorePlayer corePlayer = CorePlayer.get(target);

            ChatMessage.SPACER.sendRawMessage(sender, ChatColor.DARK_GREEN + target.getName() + "'s Active Abilities");
            ChatMessage.LINE_SPACER.sendRawMessage(sender);

            Set<AppliedEffect> totalEffects = new HashSet<>();
            totalEffects.addAll(corePlayer.getEffects().getApplied().values());
            System.out.println(totalEffects);

            for (AppliedEffect totalEffect : totalEffects) {
                if (!totalEffect.hasProperty(Property.INVISIBLE)) {
                    ChatMessage.LIST_ELEMENT.sendRawMessage(sender, "Effect", getString(totalEffect));
                }
            }
        });
    }

    @Override
    public boolean getSuggestPlayers() {
        return true;
    }

    private String getString(AppliedEffect appliedEffect) {

        Effect effect = appliedEffect.getEffect();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ChatColor.WHITE);
        stringBuilder.append(effect.getName());
        stringBuilder.append(" ");
        int level = appliedEffect.getLevel();
        if (level != 1) {
            stringBuilder.append(Roman.toRoman(level));
        }
        if (appliedEffect.hasProperty(Property.TEMPORARY)) {
            stringBuilder.append(" ");
            stringBuilder.append(ChatColor.LIGHT_PURPLE);
            stringBuilder.append("(");
            stringBuilder.append((System.currentTimeMillis() - appliedEffect.getTimestamp()) / 50);
            stringBuilder.append(" ticks");
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }
}
