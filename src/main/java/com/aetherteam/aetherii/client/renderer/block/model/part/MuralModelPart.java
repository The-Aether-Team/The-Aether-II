package com.aetherteam.aetherii.client.renderer.block.model.part;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record MuralModelPart(QuadCollection quads, boolean useAmbientOcclusion, TextureAtlasSprite particleIcon, @Nullable ChunkSectionLayer renderType) implements BlockModelPart {
    @Override
    public List<BakedQuad> getQuads(@Nullable Direction p_405263_) {
        return this.quads.getQuads(p_405263_);
    }

    @Override
    public net.minecraft.client.renderer.chunk.ChunkSectionLayer getRenderType(net.minecraft.world.level.block.state.BlockState state) {
        return this.renderType != null ? this.renderType : BlockModelPart.super.getRenderType(state);
    }
}
