package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.client.renderer.block.model.part.AOModelPart;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TriState;
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
        for (BlockModelPart part : this.delegate.collectParts(level, pos, state, random)) {
            if (part instanceof SimpleModelWrapper simpleModelWrapper) {
                parts.add(new AOModelPart(simpleModelWrapper.quads(), TriState.TRUE, simpleModelWrapper.particleIcon(), simpleModelWrapper.getRenderType(state)));
            }
        }
    }
}
