package com.aetherteam.aetherii.block.furniture;

import com.aetherteam.aetherii.blockentity.OutpostCampfireBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OutpostCampfireBlock extends MultiBlock { //todo model shapes and light and other behavior
    public static final MapCodec<OutpostCampfireBlock> CODEC = simpleCodec(OutpostCampfireBlock::new);

    public OutpostCampfireBlock(BlockBehaviour.Properties properties) {
        super(2, 2, 1, properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OutpostCampfireBlockEntity(pos, state); //todo this makes a block entity for every subblock of the multiblock, it probably should not do that.
    }
}
