package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SentryTrapBlockEntity extends GroundTrapBlockEntity {
    public SentryTrapBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_TRAP.get(), pos, blockState);
    }
}
