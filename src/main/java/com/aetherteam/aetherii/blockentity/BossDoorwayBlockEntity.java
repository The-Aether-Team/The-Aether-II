package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.BossDoorwayBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BossDoorwayBlockEntity extends CopyBlockEntity {
    public BossDoorwayBlockEntity(BlockEntityType<LockedBlockEntity> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public BossDoorwayBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.BOSS_DOORWAY_BLOCK.get(), pos, blockState);
    }

    @Override
    public BlockState open(Level level, BlockPos pos) {
        return this.getBlockState().setValue(BossDoorwayBlock.INVISIBLE, true);
    }

    @Override
    public BlockState close(Level level, BlockPos pos) {
        return this.getBlockState().setValue(BossDoorwayBlock.INVISIBLE, false);
    }

    @Override
    public BlockState destroy(Level level, BlockPos pos) {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public ItemStack getItem() {
        ItemStack stack = new ItemStack(AetherIIBlocks.BOSS_DOORWAY_BLOCK);
        stack.applyComponents(this.collectComponents());
        return stack;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
