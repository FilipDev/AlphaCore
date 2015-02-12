/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

public enum PremadeAbilities {

    GOD(false),
    DOUBLE_JUMP(true),
    AUTO_RESPAWN(false),
    NO_FALL(false),
    BYPASS_RESTRICTIONS(false),;

    private Ability ability;

    PremadeAbilities(boolean defaultEnabled) {
        this.ability = new Ability(defaultEnabled);
    }

    public Ability ability() {
        return this.ability;
    }

    public boolean isDefault() {
        return ability.isDefault();
    }
}
