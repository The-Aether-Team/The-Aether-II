package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.client.renderer.block.model.part.AOModelPart;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.ArrayList;
import java.util.List;

public class AmbientOcclusionLightModel extends DelegateBlockStateModel {
    public AmbientOcclusionLightModel(BlockStateModel originalModel) {
        super(originalModel);
    }

    @Override
    public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
        List<BlockStateModelPart> newParts = new ArrayList<>();
        this.delegate.collectParts(level, pos, state, random, newParts);
        for (BlockStateModelPart part : newParts) {
            if (part instanceof SimpleModelWrapper simpleModelWrapper) {
                parts.add(new AOModelPart(simpleModelWrapper.quads(), TriState.TRUE, simpleModelWrapper.particleMaterial()));
            }
        }
    }
}
