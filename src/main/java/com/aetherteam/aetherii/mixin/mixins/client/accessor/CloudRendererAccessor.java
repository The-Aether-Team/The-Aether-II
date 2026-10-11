package com.aetherteam.aetherii.mixin.mixins.client.accessor;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.MappableRingBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.nio.ByteBuffer;

@Mixin(CloudRenderer.class)
public interface CloudRendererAccessor {
    @Accessor("needsRebuild")
    boolean aether_ii$getNeedsRebuild();

    @Accessor("needsRebuild")
    void aether_ii$setNeedsRebuild(boolean needsRebuild);

    @Accessor("prevCellX")
    int aether_ii$getPrevCellX();

    @Accessor("prevCellX")
    void aether_ii$setPrevCellX(int prevCellX);

    @Accessor("prevCellZ")
    int aether_ii$getPrevCellZ();

    @Accessor("prevCellZ")
    void aether_ii$setPrevCellZ(int prevCellZ);

    @Accessor("prevRelativeCameraPos")
    CloudRenderer.RelativeCameraPos aether_ii$getPrevRelativeCameraPos();

    @Accessor("prevRelativeCameraPos")
    void aether_ii$setPrevRelativeCameraPos(CloudRenderer.RelativeCameraPos prevRelativeCameraPos);

    @Accessor("prevCloudStatus")
    CloudStatus aether_ii$getPrevCloudStatus();

    @Accessor("prevCloudStatus")
    void aether_ii$setPrevCloudStatus(CloudStatus prevCloudStatus);

    @Accessor("texture")
    CloudRenderer.TextureData aether_ii$getTexture();

    @Accessor("quadCount")
    int aether_ii$getQuadCount();

    @Accessor("quadCount")
    void aether_ii$setQuadCount(int quadCount);

    @Accessor("ubo")
    MappableRingBuffer aether_ii$getUbo();

    @Accessor("utb")
    MappableRingBuffer aether_ii$getUtb();

    @Accessor("utb")
    void aether_ii$setUtb(MappableRingBuffer utb);

    @Invoker
    void callBuildMesh(CloudRenderer.RelativeCameraPos relativePos, ByteBuffer faceBuffer, int centerCellX, int centerCellZ, boolean extrude, int radiusCells);

    @Invoker
    static int callGetSizeForCloudDistance(int radiusCells) {
        throw new AssertionError();
    }
}
