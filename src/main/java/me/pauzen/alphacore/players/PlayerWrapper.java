/*
 *  Created by Filip P. on 6/12/15 3:47 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.core.modules.ManagerModule;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerWrapper extends ManagerModule {

    public Player getPlayer();

    @Override
    public default void unload() {
        PlayerManager.getManager().destroyWrapper(getPlayer(), getClass());
    }

    public void load();

    public void save();

    public default UUID getUniqueId() {
        return getPlayer().getUniqueId();
    }

}
