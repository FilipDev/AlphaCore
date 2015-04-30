/*
 *  Created by Filip P. on 4/11/15 10:32 PM.
 */

package me.pauzen.alphacore.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class ParticleUtils {

    private ParticleUtils() {}

    /**
     * Displays particle in the world the player inputted is in. NOTE: Particle is visible to everyone and not just to player inputted.
     * @param particle Particle to display.
     * @param player Player to get world from.
     * @param location The location where the particle is displayed.
     * @param velocity The velocity of the particle.
     * @param speed The speed of the particle.
     * @param amount The amount of the particle to display.
     */
    public static void spawnParticle(Particle particle, Player player, Location location, Vector velocity, double speed, int amount) {
        String format = String.format("particle %s %s %s %s %s %s %s %s %s", particle.getParticle() + "", location.getX() + "", location.getY() + "", location.getZ() + "", velocity.getX() + "", velocity.getY() + "", velocity.getZ() + "", speed + "", amount + "");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), format);
    }
    
    public static void spawnParticle(Particle particle, World world, Location location, Vector velocity, int speed, int amount) {
        if (!world.getPlayers().isEmpty()) {
            ParticleUtils.spawnParticle(particle, world.getPlayers().get(0), location, velocity, speed, amount);
        }
    }
    
    public static void spawnParticle(Particle particle, World world, Location location, int speed, int amount) {
        spawnParticle(particle, world, location, new Vector(0, 0, 0), speed, amount);
    }
    
    public static void spawnParticle(Particle particle, Location location, int speed, int amount) {
        spawnParticle(particle, location.getWorld(), location, speed, amount);
    }
    
    public static void spawnParticle(Particle particle, Location location, int amount) {
        spawnParticle(particle, location, 0, amount);
    }

    public static void spawnParticle(Particle particle, Location location, Vector vector, int speed, int amount) {
        spawnParticle(particle, location.getWorld(), location, vector, speed, amount);
    }

    public static void spawnParticle(Particle particle, Location location, Vector vector, int amount) {
        spawnParticle(particle, location.getWorld(), location, vector, 0, amount);
    }

}
