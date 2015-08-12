/*
 *  Created by Filip P. on 6/28/15 12:50 AM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.core.modules.ManagerModule;
import me.pauzen.alphacore.dynamicevents.Events;
import me.pauzen.alphacore.effects.events.EffectApplyEvent;
import me.pauzen.alphacore.effects.events.EffectRemoveEvent;
import me.pauzen.alphacore.effects.exceptions.ApplicationCancellationException;
import me.pauzen.alphacore.effects.exceptions.RemovalCancellationException;
import me.pauzen.alphacore.players.CorePlayer;
import me.pauzen.alphacore.updater.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Effect implements ManagerModule {

    private final String name;
    private final long   defaultDuration;

    private Map<CorePlayer, AppliedEffect> applications = new HashMap<>();

    public Effect(String name, long defaultDuration) {
        this.name = name;
        this.defaultDuration = defaultDuration;

        Events.addExecutor(UpdateEvent.class, (event) -> {
            for (AppliedEffect appliedEffect : getApplications()) {
                update(event, appliedEffect.getCorePlayer());
            }
        });
    }

    public Effect(String name) {
        this(name, Long.MAX_VALUE / 50);
    }

    protected void apply(AppliedEffect appliedEffect) throws ApplicationCancellationException {
        EffectApplyEvent effectApplyEvent = new EffectApplyEvent(appliedEffect, this);

        if (effectApplyEvent.call().isCancelled()) {
            throw new ApplicationCancellationException();
        }

        onApply(appliedEffect);

        applications.put(appliedEffect.getCorePlayer(), appliedEffect);
    }

    protected void remove(AppliedEffect appliedEffect) throws RemovalCancellationException {
        EffectRemoveEvent effectRemoveEvent = new EffectRemoveEvent(appliedEffect, this);

        if (effectRemoveEvent.call().isCancelled()) {
            throw new RemovalCancellationException();
        }

        onRemove(appliedEffect);

        applications.remove(appliedEffect.getCorePlayer());
    }

    protected void update(UpdateEvent update, CorePlayer corePlayer) {
        AppliedEffect appliedEffect = getAppliedEffect(corePlayer);

        if (appliedEffect.elapsed()) {
            corePlayer.getEffects().deactivate(this);
            return;
        }

        onUpdate(update, appliedEffect);
    }

    public abstract void onApply(AppliedEffect applied);

    public abstract void onUpdate(UpdateEvent update, AppliedEffect applied);

    public abstract void onRemove(AppliedEffect applied);

    public String getName() {
        return name;
    }

    public long getDefaultDuration() {
        return defaultDuration;
    }

    public AppliedEffect getAppliedEffect(CorePlayer corePlayer) {
        return corePlayer.getEffects().get(getClass());
    }

    public Collection<AppliedEffect> getApplications() {
        return applications.values();
    }
    
    @Override
    public void unload() {
        for (AppliedEffect appliedEffect : getApplications()) {
            try {
                remove(appliedEffect);
            } catch (RemovalCancellationException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not unload Effect " + name);
            }
        }
    }

}
