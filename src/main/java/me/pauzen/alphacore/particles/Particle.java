/*
 *  Created by Filip P. on 4/11/15 11:15 PM.
 */

package me.pauzen.alphacore.particles;

public enum Particle {

    REDSTONE_DUST("reddust"),
    HEART("heart");

    private String particle;

    Particle(String particle) {
        this.particle = particle;
    }

    public String getParticle() {
        return particle;
    }
}
