package com.aetherteam.aetherii.client.renderer.block.model.part;

import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Direction;
import net.minecraft.util.TriState;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

//public record AOModelPart(QuadCollection quads, TriState ambientOcclusion, TextureAtlasSprite particleIcon, @Nullable ChunkSectionLayer renderType) implements BlockStateModelPart {
//    @Override
//    public List<BakedQuad> getQuads(@Nullable Direction p_405263_) {
//        return this.quads.getQuads(p_405263_);
//    }
//
//    @Override
//    public boolean useAmbientOcclusion() {
//        return true;
//    }
//
//    @Override
//    public ChunkSectionLayer getRenderType(BlockState state) {
//        return this.renderType != null ? this.renderType : BlockStateModelPart.super.getRenderType(state);
//    }
//}
