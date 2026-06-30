package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GuardianDonationBoxBlockEntity extends AetherIIRandomizableSingleItemBlockEntity {
    public GuardianDonationBoxBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.GUARDIAN_DONATION_BOX.get(), pos, state);
    }
}
