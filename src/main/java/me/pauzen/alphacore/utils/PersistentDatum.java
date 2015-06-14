/*
 *  Created by Filip P. on 5/20/15 11:35 PM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.Core;

public class PersistentDatum<T> {

    private String path;

    public PersistentDatum(String path) {
        this.path = path;
    }

    public T get() {
        return (T) Core.getPersistent().getConfiguration().get(path);
    }

    public T get(T def) {
        return (T) Core.getPersistent().getConfiguration().get(path, def);
    }

    public void set(T value) {
        Core.getPersistent().getConfiguration().set(path, value);
        save();
    }

    public void save() {
        Core.getPersistent().save();
    }

}
