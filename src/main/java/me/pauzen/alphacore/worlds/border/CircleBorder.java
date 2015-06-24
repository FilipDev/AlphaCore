/*
 *  Created by Filip P. on 6/22/15 2:19 AM.
 */

package me.pauzen.alphacore.worlds.border;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CircleBorder extends Border {

    public CircleBorder(int size, Location centre) {
        super(size, centre);
    }

    @Override
    public boolean isWithin(Player player) {
        Location location = player.getLocation();
        double x = location.getX() - getCentre().getX();
        double z = location.getZ() - getCentre().getZ();
        double distance = Math.sqrt(x * x + z * z);
        return distance < getSize();
    }

    @Override
    public void revert(Player player) {
        Location loc = player.getLocation();
        double m = (loc.getX() - getCentre().getX()) / (loc.getZ() - getCentre().getZ());

        double atan = Math.atan(m);
        double size = getSize() - Math.sqrt(getSize()) * 2;
        double x = Math.copySign(Math.sin(atan) * (size + getCentre().getX()), loc.getX());
        double z = Math.copySign(Math.cos(atan) * (size + getCentre().getZ()), loc.getZ());

        World world = loc.getWorld();
        Location location = new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z);
        location.setPitch(loc.getPitch());
        location.setYaw(loc.getYaw() - 180);

        player.teleport(location);
        player.setVelocity(new Vector(0, 0, 0));
        
        getBorderCrossMessage().send(player);
    }
}
