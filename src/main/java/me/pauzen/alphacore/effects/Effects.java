/*
 *  Created by Filip P. on 6/28/15 1:43 AM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.effects.exceptions.ApplicationCancellationException;
import me.pauzen.alphacore.effects.exceptions.RemovalCancellationException;
import me.pauzen.alphacore.players.CorePlayer;

import java.util.HashMap;
import java.util.Map;

public class Effects {

    private final Map<Class<? extends Effect>, AppliedEffect> applied = new HashMap<>();
    private CorePlayer corePlayer;

    public Effects(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
    }

    public Map<Class<? extends Effect>, AppliedEffect> getApplied() {
        return applied;
    }

    protected AppliedEffect get(Class<? extends Effect> effect) {
        return getApplied().get(effect);
    }

    protected void put(Class<? extends Effect> key, AppliedEffect value) {
        getApplied().putIfAbsent(key, value);
    }

    protected void remove(Class<? extends Effect> effect) {
        getApplied().remove(effect);
    }

    public boolean activate(Effect effect, int level, long duration) {
        AppliedEffect appliedEffect = new AppliedEffect(corePlayer, level, effect, duration);

        if (appliedEffect.getDuration() == Long.MAX_VALUE) {
            Property.TEMPORARY.add(appliedEffect);
        }
        try {
            effect.apply(appliedEffect);
        } catch (ApplicationCancellationException e) {
            return false;
        }
        put(effect.getClass(), appliedEffect);
        return true;
    }

    public boolean activate(Effect effect, int level) {
        return activate(effect, level, effect.getDefaultDuration());
    }

    /**
     * Attempts to deactivate Effect.
     *
     * @param effect Effect to deactivate.
     * @return Whether or not deactivation was successful.
     */
    public boolean deactivate(Effect effect) {
        AppliedEffect appliedEffect = getApplied().get(effect.getClass());
        try {
            effect.remove(appliedEffect);
        } catch (RemovalCancellationException e) {
            return false;
        }
        remove(effect.getClass());
        return true;
    }

    public boolean hasActivated(Effect effect) {
        return getApplied().containsKey(effect.getClass());
    }

    @Deprecated
    public boolean toggleState(Effect effect, int level) {
        if (hasActivated(effect)) {
            return !deactivate(effect);
        }
        else {
            return activate(effect, level);
        }
    }

    public void removeAll() {
        for (AppliedEffect appliedEffect : getApplied().values()) {
            deactivate(appliedEffect.getEffect());
        }
    }
}
