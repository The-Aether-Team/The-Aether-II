package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OutpostCampfireBlockEntity extends BlockEntity {
    private boolean lit;

    public OutpostCampfireBlockEntity() {
        this(BlockPos.ZERO, AetherIIBlocks.OUTPOST_CAMPFIRE.get().defaultBlockState()
                .setValue(AetherIIBlocks.OUTPOST_CAMPFIRE.get().X_OFFSET_FROM_ORIGIN, 1)
                .setValue(AetherIIBlocks.OUTPOST_CAMPFIRE.get().Y_OFFSET_FROM_ORIGIN, 0)
                .setValue(AetherIIBlocks.OUTPOST_CAMPFIRE.get().Z_OFFSET_FROM_ORIGIN, 1));
    }

    public OutpostCampfireBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.OUTPOST_CAMPFIRE.get(), pos, blockState);
    }

    public void setLit(boolean lit) {
        this.lit = lit;
    }

    public boolean isLit() {
        return this.lit;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
