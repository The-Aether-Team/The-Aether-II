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
//    @Accessor("starBuffer")
//    VertexBuffer aether_ii$getStarBuffer();
//
//    @Accessor("skyBuffer")
//    VertexBuffer aether_ii$getSkyBuffer();
//
//    @Accessor("darkBuffer")
//    VertexBuffer aether_ii$getDarkBuffer();
//
//    @Accessor("cloudBuffer")
//    VertexBuffer aether_ii$getCloudBuffer();
//
//    @Accessor("cloudBuffer")
//    void aether_ii$setCloudBuffer(VertexBuffer cloudBuffer);
//
//    @Accessor("prevCloudsType")
//    CloudStatus aether_ii$getPrevCloudsType();
//
//    @Accessor("prevCloudsType")
//    void aether_ii$setPrevCloudsType(CloudStatus prevCloudsType);
//
//    @Accessor("generateClouds")
//    boolean aether_ii$isGenerateClouds();
//
//    @Accessor("generateClouds")
//    void aether_ii$setGenerateClouds(boolean generateClouds);
//
//    @Accessor("rainSoundTime")
//    int aether_ii$getRainSoundTime();
//
//    @Accessor("rainSoundTime")
//    void aether_ii$setRainSoundTime(int time);
//
//    @Accessor("rainSizeX")
//    float[] aether_ii$getRainSizeX();
//
//    @Accessor("rainSizeZ")
//    float[] aether_ii$getRainSizeZ();
//
//    @Invoker
//    MeshData callBuildClouds(Tesselator tesselator, double x, double y, double z, Vec3 cloudColor);

    @Accessor("renderBuffers")
    RenderBuffers aether_ii$getRenderBuffers();

    @Accessor("skyRenderer")
    SkyRenderer aether_ii$getSkyRenderer();

    @Accessor("weatherEffectRenderer")
    WeatherEffectRenderer aether_ii$getWeatherEffectRenderer();

    @Accessor("destroyingBlocks")
    Int2ObjectMap<BlockDestructionProgress> aether_ii$getDestroyingBlocks();
}
