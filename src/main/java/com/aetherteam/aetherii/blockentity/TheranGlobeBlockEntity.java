package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TheranGlobeBlockEntity extends BlockEntity {

    public TheranGlobeBlockEntity() {
        this(AetherIIBlockEntityTypes.THERAN_GLOBE.get(), BlockPos.ZERO, AetherIIBlocks.THERAN_GLOBE.get().defaultBlockState());
    }

    public TheranGlobeBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.THERAN_GLOBE.get(), pos, state);
    }

    protected TheranGlobeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}