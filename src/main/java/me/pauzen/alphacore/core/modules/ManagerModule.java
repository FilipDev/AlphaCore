/*
 *  Created by Filip P. on 5/5/15 9:58 PM.
 */

package me.pauzen.alphacore.core.modules;

import me.pauzen.alphacore.Core;
import org.bukkit.plugin.java.JavaPlugin;

public interface ManagerModule extends Module {

    public default JavaPlugin getOwner() {
        return Core.getOwner(this);
    }

}
