/*
 *  Created by Filip P. on 7/1/15 5:42 PM.
 */

package me.pauzen.alphacore.places;

import me.pauzen.alphacore.border.Border;
import me.pauzen.alphacore.commands.Command;
import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.server.CoreServer;
import me.pauzen.alphacore.utils.AllowanceChecker;

import java.util.Set;

public interface Place extends ManagerModule {

    public static Place getServerPlace() {
        return CoreServer.SERVER_PLACE;
    }

    Set<CorePlayer> getPlayers();

    String getName();

    boolean isAllowed(Command command);

    boolean isAllowed(PlaceAction placeAction);

    Place getSuperPlace();

    Border getBorder();

    void setBorder(Border border);

    AllowanceChecker<PlaceAction> getPlaceActionChecker();
    
    AllowanceChecker<String> getCommandChecker();
}
