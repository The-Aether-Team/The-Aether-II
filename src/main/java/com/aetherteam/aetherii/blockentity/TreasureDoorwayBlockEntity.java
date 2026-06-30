package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TreasureDoorwayBlockEntity extends CopyBlockEntity {
    public TreasureDoorwayBlockEntity(BlockEntityType<TreasureDoorwayBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public TreasureDoorwayBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.TREASURE_DOORWAY_BLOCK.get(), pos, blockState);
    }

    @Override
    public BlockState destroy(Level level, BlockPos pos) {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public ItemStack getItem() {
        ItemStack stack = new ItemStack(AetherIIBlocks.TREASURE_DOORWAY_BLOCK.get());
        if (this.copyState != null) {
            AetherIIDataComponents.set(stack, AetherIIDataComponents.BLOCK_STATE, this.copyState);
            CompoundTag tag = new CompoundTag();
            this.saveCopyState(tag);
            BlockItem.setBlockEntityData(stack, this.getType(), tag);
        }
        return stack;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
