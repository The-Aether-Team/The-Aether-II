package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AmbrosiumCampfireBlockEntity extends CampfireBlockEntity {
    public AmbrosiumCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<AmbrosiumCampfireBlockEntity> getType() {
        return AetherIIBlockEntityTypes.AMBROSIUM_CAMPFIRE.get();
    }
}