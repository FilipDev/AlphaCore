/*
 *  Created by Filip P. on 2/6/15 6:05 PM.
 */

package me.pauzen.alphacore.utils;

import me.pauzen.alphacore.players.CorePlayer;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class SoundUtils {

    private SoundUtils() {
    }
    
    public static void playSound(CorePlayer corePlayer, Sound sound, float volume, float pitch) {
        playSound(corePlayer.getPlayer(), sound, volume, pitch);
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playSound(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }
    
    public static void playSound(World world, Sound sound, final float volume, final float pitch) {
        world.getPlayers().forEach(player -> playSound(player, sound, volume, pitch));
    }
    
}
