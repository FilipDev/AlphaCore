/*
 *  Created by Filip P. on 6/27/15 3:33 PM.
 */

package me.pauzen.alphacore.entities.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public final class HeadRotation {

    private final float pitch, yaw;

    public HeadRotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public static HeadRotation calculate(Location from, Location to) {
        double x = from.getX() - to.getX();
        double z = from.getZ() - to.getZ();

        float yaw = (float) -(Math.toDegrees(Math.atan(x / z)));

        double distance = Math.sqrt(x * x + z * z);

        double c = from.getY() - to.getY();

        float pitch = (float) (Math.toDegrees(Math.atan(c / distance)) % 180);

        return new HeadRotation(pitch, yaw);
    }
    
    public void apply(Entity entity) {
        Location location = entity.getLocation();
        location.setPitch(pitch);
        location.setYaw(yaw);
        entity.teleport(location);
    }

}
