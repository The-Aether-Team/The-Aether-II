package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MusicManager.class)
public interface MusicManagerAccessor {
    @Accessor("currentMusic")
    SoundInstance aether_ii$getCurrentMusic();
}
