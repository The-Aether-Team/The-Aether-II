package com.aetherteam.aetherii.client.renderer.block.model.blockstate;

import com.aetherteam.aetherii.blockentity.LockedBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SimpleModelWrapper;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;

import java.util.ArrayList;
import java.util.List;

public class LockedBlockModel extends DelegateBlockStateModel {
    public LockedBlockModel(BlockStateModel delegate) {
        super(delegate);
    }

    @Override
    public void collectParts(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockState blockState, RandomSource randomSource, List<BlockModelPart> list) {
        LockedBlockEntity.LockedData data = blockAndTintGetter.getModelData(blockPos).get(LockedBlockEntity.LockedData.PROPERTY);
        if (data == null) {
            return;
        }
        BlockState state = data.state();
        List<BlockModelPart> newParts = new ArrayList<>();
        Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(state).collectParts(blockAndTintGetter, blockPos, state, randomSource, newParts);
        for (BlockModelPart part : newParts) {
            if (part instanceof SimpleModelWrapper simpleModelWrapper && simpleModelWrapper.getRenderType(state) != ChunkSectionLayer.SOLID) {
                list.add(new SimpleModelWrapper(simpleModelWrapper.quads(), simpleModelWrapper.useAmbientOcclusion(), this.particleIcon(blockAndTintGetter, blockPos, blockState), ChunkSectionLayer.SOLID));
            } else {
                list.add(part);
            }
        }
    }

    @Override
    public TextureAtlasSprite particleIcon(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        LockedBlockEntity.LockedData data = level.getModelData(pos).get(LockedBlockEntity.LockedData.PROPERTY);
        if (data == null) {
            return super.particleIcon(level, pos, state);
        }
        BlockState mimicState = data.state();
        return Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(mimicState).particleIcon(level, pos, mimicState);
    }
}
