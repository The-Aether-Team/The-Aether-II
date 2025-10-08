package com.aetherteam.aetherii.client.renderer.block.model;

import com.aetherteam.aetherii.AetherII;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.ArrayList;
import java.util.List;

public class AmbientOcclusionLightModel extends DelegateBlockStateModel {
    public AmbientOcclusionLightModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        for (BlockModelPart part : this.delegate.collectParts(level, pos, state, random)) {
            QuadCollection.Builder builder = new QuadCollection.Builder();
            for (Direction side : Direction.values()) {
                for (BakedQuad quad : part.getQuads(side)) {
                    builder.addCulledFace(side, new BakedQuad(quad.vertices(), quad.tintIndex(), quad.direction(), quad.sprite(), quad.shade(), quad.lightEmission(), true));
                }
            }
            parts.add(new SimpleModelWrapper(builder.build(), true, part.particleIcon(), part.getRenderType(state)));
        }
    }
}
