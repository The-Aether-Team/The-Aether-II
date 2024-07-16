package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class OutpostCampfireBlockEntity extends MultiBlockEntity {
    public OutpostCampfireBlockEntity() {
        this(BlockPos.ZERO, AetherIIBlocks.OUTPOST_CAMPFIRE.get().defaultBlockState());
    }

    public OutpostCampfireBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get(), pos, blockState);
    }
}
