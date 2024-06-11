package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AetherSignBlockEntity extends SignBlockEntity {
    public AetherSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<AetherSignBlockEntity> getType() {
        return AetherIIBlockEntityTypes.AETHER_SIGN.get();
    }
}