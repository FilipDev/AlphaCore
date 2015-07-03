/*
 *  Created by Filip P. on 6/22/15 2:23 AM.
 */

package me.pauzen.alphacore.border;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SquareBorder extends Border {

    private int reversionDistance = 5;

    public SquareBorder(int size, Location centre) {
        super(size, centre);
    }

    @Override
    public boolean isWithin(Player player) {
        return Math.abs(player.getLocation().getBlockX() - getCentre().getBlockX()) < getSize() && Math.abs(player.getLocation().getBlockZ() - getCentre().getBlockZ()) < getSize();
    }

    @Override
    public void revert(Player player) {
        Location location = player.getLocation();

        Location clone = location.clone();

        int x = location.getBlockX();
        int z = location.getBlockZ();

        if (Math.abs(x) >= getSize() + Math.copySign(getCentre().getBlockX(), x)) {
            clone.setX(Math.copySign(x - Math.copySign(reversionDistance, x), x));
        }

        if (Math.abs(z) >= getSize() + Math.copySign(getCentre().getBlockX(), x)) {
            clone.setZ(Math.copySign(x - Math.copySign(reversionDistance, z), z));
        }

        clone.setYaw(location.getYaw() - 180);

        player.teleport(clone);

        getBorderCrossMessage().send(player);

    }

    public int getReversionDistance() {
        return reversionDistance;
    }

    public void setReversionDistance(int reversionDistance) {
        this.reversionDistance = reversionDistance;
    }
}
