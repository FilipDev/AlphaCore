/*
 *  Created by Filip P. on 3/21/15 5:23 PM.
 */

package me.pauzen.alphacore.emitters;

import me.pauzen.alphacore.utils.SoundUtils;
import org.bukkit.Sound;

public class SoundEmitter extends Emitter {
    
    private float volume;
    private float pitch;
    
    private Sound sound;
    
    public SoundEmitter volume(float volume) {
        this.volume = volume / 10;
        return this;
    }
    
    public SoundEmitter pitch(float pitch) {
        this.pitch = pitch;
        return this;
    }
    
    public SoundEmitter sound(Sound sound) {
        this.sound = sound;
        return this;
    }

    @Override
    public void emit() {
        SoundUtils.playSound(getLocation(), sound, volume, pitch);
    }
}
