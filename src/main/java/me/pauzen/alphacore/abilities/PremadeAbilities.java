/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

public enum PremadeAbilities {

    GOD(false),
    HUNGER_GOD(false),
    DOUBLE_JUMP(true),
    AUTO_RESPAWN(false),
    NO_FALL(false),
    BYPASS_RESTRICTIONS(false),
    CHAT(false),
    INSTANT_BREAK(false),
    ;

    private Ability ability;

    PremadeAbilities(boolean defaultEnabled) {
        this.ability = new Ability(toName(name()), defaultEnabled);
    }

    public Ability ability() {
        return this.ability;
    }

    public boolean isDefault() {
        return ability.isDefault();
    }
    
    private static String toName(String name) {
        String[] split = name.split("_");

        StringBuilder finalString = new StringBuilder();
        
        for (int i = 0; i < split.length; i++) {
            if (i != 0) {
                finalString.append(" ");
            }

            String part = split[i];

            finalString.append(part.substring(0, 1));
            finalString.append(part.substring(1).toLowerCase());
        }
        
        return finalString.toString();
    }
}
