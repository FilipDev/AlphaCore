/*
 *  Created by Filip P. on 5/19/15 5:04 PM.
 */

package me.pauzen.alphacore.worlds;

import java.util.function.Consumer;

public enum WorldProperty {

    LOCK_TIME,
    LOCK_WEATHER,

    PREVENT_JOINING,;

    private Consumer<CoreWorld> onApply  = (world) -> {
    };
    private Consumer<CoreWorld> onRemove = (world) -> {
    };

    WorldProperty() {
    }

    WorldProperty(Consumer<CoreWorld> onApply, Consumer<CoreWorld> onRemove) {
        this.onApply = onApply;
        this.onRemove = onRemove;
    }

    public void onApply(CoreWorld coreWorld) {
        onApply.accept(coreWorld);
    }

    public void onRemove(CoreWorld coreWorld) {
        onRemove.accept(coreWorld);
    }
}
