/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.largeplugincore.abilities;

public enum ExampleAbilities {
    
    GOD(false),
    DOUBLE_JUMP(true),
    ;
    
    private Ability ability;
    
    ExampleAbilities(boolean defaultEnabled) {
        this.ability = new Ability(defaultEnabled);
    }
    
    public Ability ability() {
        return this.ability;
    }

    public boolean isDefault() {
        return ability.isDefault();
    }
}
