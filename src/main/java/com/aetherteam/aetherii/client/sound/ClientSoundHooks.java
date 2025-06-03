package com.aetherteam.aetherii.client.sound;

import com.aetherteam.aetherii.client.sound.instance.DiggingSoundInstance;
import com.aetherteam.aetherii.entity.DiggingMob;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Mob;

public class ClientSoundHooks {
    public static <T extends Mob & DiggingMob> void playDiggingSoundInstance(T entity, SoundEvent soundEvent) {
        Minecraft.getInstance().getSoundManager().play(new DiggingSoundInstance<>(entity, soundEvent));
    }
}
