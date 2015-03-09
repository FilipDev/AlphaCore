/*
 *  Created by Filip P. on 2/6/15 7:02 PM.
 */

package me.pauzen.alphacore.effects;

import me.pauzen.alphacore.abilities.Ability;
import me.pauzen.alphacore.abilities.PremadeAbilities;
import me.pauzen.alphacore.players.CorePlayer;

public enum PremadeEffects {

    NO_BLOCK_BREAK("No Block Break"),
    NO_BLOCK_PLACE("No Block Place"),
    NO_SHOOT_BOW("No Bow Shoot", 15),
    ;

    private Effect effect;

    PremadeEffects(String name, long duration) {
        this.effect = new Effect(name, duration) {
            @Override
            public void onApply(CorePlayer corePlayer) {
            }

            @Override
            public void onRemove(CorePlayer corePlayer) {
            }

            @Override
            public void perSecond(CorePlayer corePlayer) {
            }
        };
    }

    PremadeEffects(Effect effect) {
        this.effect = effect;
    }

    PremadeEffects(String name, Ability ability) {
        this(new Effect(name) {
            @Override
            public void onApply(CorePlayer corePlayer) {
                corePlayer.toggleAbilityState(ability);
            }

            @Override
            public void onRemove(CorePlayer corePlayer) {
                corePlayer.toggleAbilityState(ability);
            }

            @Override
            public void perSecond(CorePlayer corePlayer) {
            }
        });
    }

    PremadeEffects(String name) {
        this(name, -1);
    }

    public Effect effect() {
        return this.effect;
    }

}

