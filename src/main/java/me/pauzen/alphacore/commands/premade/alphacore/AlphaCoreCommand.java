/*
 *  Created by Filip P. on 3/7/15 11:25 PM.
 */

package me.pauzen.alphacore.commands.premade.alphacore;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.commands.CommandMeta;
import me.pauzen.alphacore.messages.ChatMessage;

@CommandMeta(value = "alphacore", aliases = "ac")
public class AlphaCoreCommand extends Command {

    @Override
    public void onRegister() {
        System.out.println("asdfasdfasdf");
        Modules modules = new Modules();
        Help help = new Help();
        addSubCommands(modules, help);
    }

    @Override
    public CommandListener getDefaultListener() {
        return new CommandListener(null, true) {
            @Override
            public void onRun() {
                ChatMessage.ABOUT.send(sender);
            }
        };
    }
}
