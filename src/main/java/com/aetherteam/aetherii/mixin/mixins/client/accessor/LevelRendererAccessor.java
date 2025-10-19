package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.server.level.BlockDestructionProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessor {
    @Accessor("renderBuffers")
    RenderBuffers aether_ii$getRenderBuffers();

    @Accessor("skyRenderer")
    SkyRenderer aether_ii$getSkyRenderer();

    @Accessor("weatherEffectRenderer")
    WeatherEffectRenderer aether_ii$getWeatherEffectRenderer();

    @Accessor("destroyingBlocks")
    Int2ObjectMap<BlockDestructionProgress> aether_ii$getDestroyingBlocks();
}
