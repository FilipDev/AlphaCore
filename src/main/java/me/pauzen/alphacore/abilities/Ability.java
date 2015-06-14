/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

import me.pauzen.alphacore.applicable.Applicable;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandManager;
import me.pauzen.alphacore.effects.Effect;
import me.pauzen.alphacore.listeners.ListenerImplementation;
import me.pauzen.alphacore.messages.ChatMessage;
import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class Ability extends ListenerImplementation implements Applicable {

    private Effect effect;
    private String abilityName;

    private boolean invisible = false;

    private Set<UUID> affected = new HashSet<>();

    public Ability(String abilityName) {
        this.abilityName = abilityName;
        this.effect = new Effect(abilityName) {

            @Override
            public void onApply(CorePlayer cPlayer) {
                cPlayer.activateAbility(Ability.this);
            }

            @Override
            public void onRemove(CorePlayer cPlayer) {
                cPlayer.deactivateAbility(Ability.this);
            }

            @Override
            public void perSecond(CorePlayer cPlayer) {
            }
        };
    }

    /**
     * @param abilityName Name to give Ability. Refer to getName.
     * @param invisible   Whether or not the ability appears on ActiveCommand.
     */
    public Ability(String abilityName, boolean invisible) {
        this(abilityName);
        this.invisible = invisible;
    }

    /**
     * Returns ability boolean state in form to be used in chat.
     *
     * @param toggled Toggle state.
     * @return Formatted string according to toggle state.
     */
    public static String booleanToState(boolean toggled) {
        return toggled ? ChatColor.GREEN + "activated" : ChatColor.RED + "deactivated";
    }

    /**
     * Sets state of ability for specific player.
     *
     * @param ability    Ability to set.
     * @param corePlayer Player to set ability for.
     */
    public static void setAbilityState(Ability ability, CorePlayer corePlayer, boolean newState) {
        ChatMessage.SET.send(corePlayer, ChatColor.BLUE + ability.getName() + ChatColor.RESET, Ability.booleanToState(corePlayer.setAbilityState(ability, newState)));
    }

    /**
     * Toggles state of ability for specific player.
     *
     * @param ability    Ability to toggle.
     * @param corePlayer Player to toggle ability for.
     */
    public static void toggleAbilityState(Ability ability, CorePlayer corePlayer) {

        boolean state = corePlayer.toggleAbilityState(ability);

        AbilityStateChangeEvent stateChangeEvent = new AbilityStateChangeEvent(corePlayer, ability, state);

        stateChangeEvent.call();

        ChatMessage.TOGGLED.send(corePlayer, ability.getName(), Ability.booleanToState(state));
    }

    /**
     * Adds player to set of affected players.
     *
     * @param uuid Player uuid.
     */
    public void addAffected(UUID uuid) {
        affected.add(uuid);
    }

    /**
     * Removes player from set of affected players.
     *
     * @param uuid Player uuid.
     */
    public void removeAffected(UUID uuid) {
        affected.remove(uuid);
    }

    /**
     * Set of Players with the Ability.
     *
     * @return Player set.
     */
    public Set<UUID> getAffected() {
        return affected;
    }

    /**
     * Returns name of Ability used in ActiveCommand and other times when referring to Ability.
     *
     * @return Ability name.
     */
    @Override
    public String getName() {
        return abilityName;
    }

    /**
     * Returns Ability in Effect form which when applied to a player applies the ability and vice versa.
     *
     * @return Ability in Effect form.
     */
    public Effect asEffect() {
        return this.effect;
    }

    /**
     * Applies ability to a player.
     *
     * @param corePlayer Player to apply ability to.
     */
    @Override
    public void apply(CorePlayer corePlayer) {
        apply(corePlayer, 1);
    }

    public void apply(CorePlayer corePlayer, int level) {
        onApply(corePlayer, level);
        corePlayer.activateAbility(this, level);
    }

    /**
     * Removes ability from player.
     *
     * @param corePlayer Player to remove ability from.
     */
    @Override
    public void remove(CorePlayer corePlayer) {
        corePlayer.deactivateAbility(this);
    }

    /**
     * If the player has the ability activated.
     *
     * @param corePlayer Player to check.
     * @return Whether or not the player has the ability activated.
     */
    @Override
    public boolean hasActivated(CorePlayer corePlayer) {
        return corePlayer.hasActivated(this);
    }

    public void setAbilityState(CorePlayer corePlayer, boolean newState) {
        setAbilityState(this, corePlayer, newState);
    }

    public void toggleAbilityState(CorePlayer corePlayer) {
        toggleAbilityState(this, corePlayer);
    }

    /**
     * Whether or not the Ability shows up the Ability list.
     *
     * @return State of invisibility.
     */
    @Override
    public boolean isInvisible() {
        return invisible;
    }

    /**
     * Creates a command based on the state of the ability. Running the command toggles the ability, can use the modifier -set (state)
     * to set the ability state.
     *
     * @param name        The command name.
     * @param aliases     Aliases for the command name.
     * @param description Command description.
     * @param permissions Permissions required to run command.
     * @return The new command based on the given parameters.
     */
    public Command asCommand(String name, String[] aliases, String description, String[] permissions) {
        Command command = new Command(name, aliases, description) {
            @Override
            public CommandListener getDefaultListener() {
                return new CommandListener(false, permissions) {
                    @Override
                    public void onRun() {

                        if (modifiers.containsKey("set")) {
                            boolean setState = Boolean.parseBoolean(modifiers.get("set"));
                            set(Ability.this, CorePlayer.get((Player) sender), setState);
                            return;
                        }

                        toggle(Ability.this, CorePlayer.get((Player) sender));
                    }

                    private void set(Ability ability, CorePlayer corePlayer, boolean state) {
                        ability.setAbilityState(corePlayer, state);
                    }

                    private void toggle(Ability ability, CorePlayer corePlayer) {
                        ability.toggleAbilityState(corePlayer);
                    }
                };
            }
        };

        List<String> names = new ArrayList<>();
        Collections.addAll(names, name.split(" "));

        if (names.size() > 1) {
            Command previous = CommandManager.getManager().getCommand(names.get(names.size() - 2));

            if (previous != null) {
                command.setParent(previous);
            }
        }

        return command;
    }

    public Command asCommand(String name, String... permissions) {
        return asCommand(name, new String[]{}, "%default%", permissions);
    }

    public Command asCommand(String name, String[] aliases, String... permissions) {
        return asCommand(name, aliases, "%default%", permissions);
    }

    public void onApply(CorePlayer corePlayer, int level) {
    }

    public void onRemove(CorePlayer corePlayer) {
    }
}
