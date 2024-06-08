package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootBedBlockEntity extends BlockEntity {
    public SkyrootBedBlockEntity() {
        super(AetherIIBlockEntityTypes.SKYROOT_BED.get(), BlockPos.ZERO, AetherIIBlocks.SKYROOT_BED.get().defaultBlockState());
    }

    public SkyrootBedBlockEntity(BlockPos pos, BlockState state) {
        super(AetherIIBlockEntityTypes.SKYROOT_BED.get(), pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
