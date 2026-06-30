package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockEntity extends BlockEntity {
    private BlockPos levelOriginPos;

    public MultiBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("origin")) {
            int[] positions = tag.getIntArray("origin");
            if (positions.length >= 3) {
                this.levelOriginPos = new BlockPos(positions[0], positions[1], positions[2]);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.levelOriginPos != null) {
            tag.putIntArray("origin", new int[]{this.levelOriginPos.getX(), this.levelOriginPos.getY(), this.levelOriginPos.getZ()});
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    public void setLevelOriginPos(BlockPos levelOriginPos) {
        this.levelOriginPos = levelOriginPos;
    }

    public BlockPos getLevelOriginPos() {
        return this.levelOriginPos;
    }

    public boolean isOrigin() {
        return this.getBlockPos().equals(this.getLevelOriginPos());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
