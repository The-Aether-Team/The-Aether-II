package com.aetherteam.aetherii.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.List;

public class FastModel extends DelegateBlockStateModel {
    public FastModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        for (BlockModelPart part : this.delegate.collectParts(level, pos, state, random)) {
            if (part instanceof SimpleModelWrapper simpleModelWrapper) {
                parts.add(new SimpleModelWrapper(simpleModelWrapper.quads(), simpleModelWrapper.useAmbientOcclusion(), simpleModelWrapper.particleIcon(), Minecraft.useFancyGraphics() ? ChunkSectionLayer.CUTOUT_MIPPED : ChunkSectionLayer.SOLID));
            }
        }
    }
}
