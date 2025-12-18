package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class LockedBlockEntity extends CopyBlockEntity {
    public LockedBlockEntity(BlockEntityType<LockedBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public LockedBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.LOCKED_BLOCK.get(), pos, blockState);
    }

    @Override
    public BlockState destroy(Level level, BlockPos pos) {
        return this.getCopyState();
    }

    @Override
    public ItemStack getItem() {
        ItemStack stack = new ItemStack(AetherIIBlocks.LOCKED_BLOCK);
        stack.applyComponents(this.collectComponents());
        return stack;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
