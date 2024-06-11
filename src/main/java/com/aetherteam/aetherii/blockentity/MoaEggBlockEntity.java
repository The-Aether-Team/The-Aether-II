package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MoaEggBlockEntity extends BlockEntity {
    public int tickCount;

    public MoaEggBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.MOA_EGG.get(), pos, state);
    }


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MoaEggBlockEntity moaEggBlockEntity) {
        moaEggBlockEntity.tickCount++;
    }
}