/*
 *  Created by Filip P. on 2/11/15 11:42 PM.
 */

package me.pauzen.alphacore.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AllowanceChecker<T> {

    private Set<T> allowed = new HashSet<>(), disallowed = new HashSet<>();

    @SafeVarargs
    public final void disallow(T... disallowed) {
        Collections.addAll(this.disallowed, disallowed);
    }

    @SafeVarargs
    public final void allow(T... allowed) {
        Collections.addAll(this.allowed, allowed);
    }

    public Set<T> getDisallowed() {
        return disallowed;
    }

    public Set<T> getAllowed() {
        return allowed;
    }

    public boolean isAllowed(T object) {
        if (!allowed.isEmpty()) {
            return allowed.contains(object);
        }
        if (!disallowed.isEmpty()) {
            return !disallowed.contains(object);
        }

        return true;
    }

}
