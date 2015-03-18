/*
 *  Created by Filip P. on 2/2/15 11:24 PM.
 */

package me.pauzen.alphacore.abilities;

import me.pauzen.alphacore.abilities.premade.*;
import me.pauzen.alphacore.utils.misc.Todo;

public enum PremadeAbilities {

    @Todo("Create class for every one of these eliminating the requirement for this enum.")
    HUNGER_GOD(new HungerGod()),
    GOD(new God()),
    NO_FALL(new NoFall()),
    NO_TARGET(new NoTarget()),
    DOUBLE_JUMP(new DoubleJumpAbility()),
    INSTANT_BREAK(new Ability("Instant Break")),;

    private Ability ability;

    PremadeAbilities(Ability ability) {
        this.ability = ability;
    }

    public Ability ability() {
        return this.ability;
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
