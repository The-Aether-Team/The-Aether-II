package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.ArrayList;
import java.util.List;

public class CopyBlockModel extends DelegateBlockStateModel {
    public CopyBlockModel(BlockStateModel delegate) {
        super(delegate);
    }

    @Override
    public void collectParts(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, RandomSource randomSource, List<BlockModelPart> list) {
        LockedBlockEntity.CopyData data = blockAndTintGetter.getModelData(blockPos).get(LockedBlockEntity.CopyData.PROPERTY);
        if (data == null) {
            return;
        }
        BlockState state = data.state();
        List<BlockStateModelPart> newParts = new ArrayList<>();
        Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(state).collectParts(blockAndTintGetter, blockPos, state, randomSource, newParts);
        for (BlockStateModelPart part : newParts) {
            if (part instanceof SimpleModelWrapper simpleModelWrapper && simpleModelWrapper.getRenderType(state) != ChunkSectionLayer.SOLID) {
                list.add(new SimpleModelWrapper(simpleModelWrapper.quads(), simpleModelWrapper.useAmbientOcclusion(), this.particleMaterial(blockAndTintGetter, blockPos, blockState), ChunkSectionLayer.SOLID));
            } else {
                list.add(part);
            }
        }
    }

    @Override
    public TextureAtlasSprite particleMaterial(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        LockedBlockEntity.CopyData data = level.getModelData(pos).get(LockedBlockEntity.CopyData.PROPERTY);
        if (data == null) {
            return super.particleMaterial(level, pos, state);
        }
        BlockState mimicState = data.state();
        return Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(mimicState).particleMaterial(level, pos, mimicState);
    }
}
