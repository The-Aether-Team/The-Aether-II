package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OutpostCampfireBlockEntity extends BlockEntity { //todo light and other behavior
    private final boolean isActive;

    public OutpostCampfireBlockEntity() {
        this(BlockPos.ZERO, AetherIIBlocks.OUTPOST_CAMPFIRE.get().defaultBlockState()
                .setValue(AetherIIBlocks.OUTPOST_CAMPFIRE.get().X_OFFSET_FROM_ORIGIN, 1)
                .setValue(AetherIIBlocks.OUTPOST_CAMPFIRE.get().Y_OFFSET_FROM_ORIGIN, 0)
                .setValue(AetherIIBlocks.OUTPOST_CAMPFIRE.get().Z_OFFSET_FROM_ORIGIN, 1),
                false);
    }

    public OutpostCampfireBlockEntity(BlockPos pos, BlockState blockState) {
        this(pos, blockState, false);
    }

    public OutpostCampfireBlockEntity(BlockPos pos, BlockState blockState, boolean isActive) {
        super(AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get(), pos, blockState);
        this.isActive = isActive;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
