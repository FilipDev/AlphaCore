/*
 *  Created by Filip P. on 6/12/15 3:47 PM.
 */

package me.pauzen.alphacore.players;

import me.pauzen.alphacore.core.modules.ManagerModule;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class PlayerWrapper implements ManagerModule {

    public PlayerWrapper(Player player) {
    }

    public abstract Player getPlayer();

    @Override
    public void unload() {
        PlayerManager.getManager().destroyWrapper(getPlayer(), getClass());
    }

    public abstract void load();

    public abstract void save();

    public UUID getUniqueId() {
        return getPlayer().getUniqueId();
    }

}
