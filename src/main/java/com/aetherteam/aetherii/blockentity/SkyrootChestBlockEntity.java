package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootChestBlockEntity extends ChestBlockEntity {
    public SkyrootChestBlockEntity() {
        this(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), BlockPos.ZERO, AetherIIBlocks.SKYROOT_CHEST.get().defaultBlockState());
    }

    public SkyrootChestBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.SKYROOT_CHEST.get(), pos, state);
    }

    protected SkyrootChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}