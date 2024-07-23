package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MultiBlockEntity extends BlockEntity {
    private BlockPos levelOriginPos;

    public MultiBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("origin")) {
            int[] positions = tag.getIntArray("origin");
            this.levelOriginPos = new BlockPos(positions[0], positions[1], positions[2]);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.levelOriginPos != null) {
            tag.putIntArray("origin", List.of(this.levelOriginPos.getX(), this.levelOriginPos.getY(), this.levelOriginPos.getZ()));
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.loadAdditional(tag, lookupProvider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        this.saveAdditional(tag, registries);
        return tag;
    }

    public void setLevelOriginPos(BlockPos levelOriginPos) {
        this.levelOriginPos = levelOriginPos;
    }

    public BlockPos getLevelOriginPos() {
        return this.levelOriginPos;
    }

    public boolean isOrigin() {
        return this.getLevelOriginPos() == this.getBlockPos();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
