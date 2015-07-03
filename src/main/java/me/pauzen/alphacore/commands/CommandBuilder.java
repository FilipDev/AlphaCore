/*
 *  Created by Filip P. on 4/5/15 7:47 PM.
 */

package me.pauzen.alphacore.commands;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class CommandBuilder {

    private Set<String> aliases = new HashSet<>();
    private String name;
    private Set<String> permissions = new HashSet<>();
    private String      description = "%default%";

    private CommandListener commandListener;
    private boolean suggestNames = false;

    public CommandBuilder(@Nonnull String name) {
        this.name = name;
    }

    public CommandBuilder onRun(@Nonnull CommandListener listener) {
        this.commandListener = listener;
        return this;
    }
    
    public CommandBuilder onRun(@Nonnull ICommandListener listener, boolean canConsoleExecute) {
        return onRun(new CommandListener(listener, canConsoleExecute));
    }

    public CommandBuilder alias(String... aliases) {
        Collections.addAll(this.aliases, aliases);
        return this;
    }

    public CommandBuilder permissions(String... permissions) {
        Collections.addAll(this.permissions, permissions);
        return this;
    }

    public CommandBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder suggestNames(boolean flag) {
        this.suggestNames = flag;
        return this;
    }

    public Command build() {
        if (commandListener == null) {
            throw new NullPointerException("CommandListener cannot be null.");
        }

        this.commandListener.testForPermissions(permissions.toArray(new String[permissions.size()]));

        return new Command(name, aliases.toArray(new String[aliases.size()]), description) {
            @Override
            public CommandListener getDefaultListener() {
                return commandListener;
            }

            @Override
            public boolean getSuggestPlayers() {
                return suggestNames;
            }
        };
    }

}
