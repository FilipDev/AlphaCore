/*
 *  Created by Filip P. on 6/27/15 3:33 PM.
 */

package me.pauzen.alphacore.entities.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class Rotation {

    private final float pitch, yaw;

    public Rotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public static Rotation calculate(Vector from, Vector to) {
        double x = from.getX() - to.getX();
        double z = from.getZ() - to.getZ();

        float yaw = (float) -(Math.toDegrees(Math.atan(x / z)));

        double distance = Math.sqrt(x * x + z * z);

        double c = from.getY() - to.getY();

        float pitch = (float) (Math.toDegrees(Math.atan(c / distance)) % 180);

        return new Rotation(pitch, yaw);
    }

    public static Rotation calculate(Location from, Location to) {
        return calculate(from.toVector(), to.toVector());
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void apply(Entity entity) {
        Location location = entity.getLocation();
        location.setPitch(pitch);
        location.setYaw(yaw);
        entity.teleport(location);
    }

    @Override
    public String toString() {
        return "Rotation{" +
                "pitch=" + pitch +
                ", yaw=" + yaw +
                '}';
    }
}
