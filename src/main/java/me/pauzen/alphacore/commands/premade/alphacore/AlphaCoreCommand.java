/*
 *  Created by Filip P. on 3/7/15 11:25 PM.
 */

package me.pauzen.alphacore.commands.premade.alphacore;

import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.commands.CommandListener;
import me.pauzen.alphacore.messages.ChatMessage;

public class AlphaCoreCommand extends Command {

    @Override
    public String[] getAliases() {
        return new String[]{"ac"};
    }
    
    @Override
    public String getName() {
        return "alphacore";
    }

    @Override
    public CommandListener defaultListener() {
        return new CommandListener(true) {{
                sub(new Modules(), new Help());
            }

            @Override
            public void onRun() {
                ChatMessage.ABOUT.send(commandSender);
            }
        };
    }
}
