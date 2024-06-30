package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OutpostCampfireBlockEntity extends BlockEntity {
    public OutpostCampfireBlockEntity() {
        super(AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get(), BlockPos.ZERO, AetherIIBlocks.OUTPOST_CAMPFIRE.get().defaultBlockState());
    }

    public OutpostCampfireBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get(), pos, blockState);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
