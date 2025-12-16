package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;

public class LockedBlockEntity extends CopyBlockEntity {
    public LockedBlockEntity(BlockEntityType<LockedBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public LockedBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.LOCKED_BLOCK.get(), pos, blockState);
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
