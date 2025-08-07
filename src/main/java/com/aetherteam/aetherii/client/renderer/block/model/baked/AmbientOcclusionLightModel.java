package com.aetherteam.aetherii.client.renderer.block.model.baked;

import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.List;

public class AmbientOcclusionLightModel extends DelegateBlockStateModel {
    public AmbientOcclusionLightModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        parts.replaceAll((part) -> new SimpleModelWrapper(new QuadCollection.Builder().build(), true, part.particleIcon(), part.getRenderType(state)));
    }
}
