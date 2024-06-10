package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AetherHangingSignBlockEntity  extends HangingSignBlockEntity {
    public AetherHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<AetherHangingSignBlockEntity> getType() {
        return AetherIIBlockEntityTypes.AETHER_HANGING_SIGN.get();
    }
}
