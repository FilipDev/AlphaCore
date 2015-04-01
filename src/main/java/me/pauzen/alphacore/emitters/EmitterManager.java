/*
 *  Created by Filip P. on 3/21/15 4:45 PM.
 */

package me.pauzen.alphacore.emitters;

import me.pauzen.alphacore.utils.reflection.Nullify;
import me.pauzen.alphacore.utils.reflection.Registrable;

public class EmitterManager implements Registrable {

    @Nullify
    private static EmitterManager manager;

    public static EmitterManager getManager() {
        return manager;
    }

    public static void register() {
        manager = new EmitterManager();
    }
}
