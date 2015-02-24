/*
 *  Created by Filip P. on 2/23/15 10:19 PM.
 */

package me.pauzen.alphacore.updater;

public enum LoadPriority implements Comparable<LoadPriority> {

    FIRST(0),
    HIGHEST(1),
    HIGH(2),
    NORMAL(3),
    LOW(4),
    LOWEST(5),
    LAST(6);

    int position;

    LoadPriority(int position) {
        this.position = position;
    }
}
