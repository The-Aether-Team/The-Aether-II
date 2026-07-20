package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LevelEventHandler.class)
public interface LevelEventHandlerAccessor {
    @Accessor("playingJukeboxSongs")
    Map<BlockPos, SoundInstance> aether_ii$getPlayingJukeboxSongs();
}
