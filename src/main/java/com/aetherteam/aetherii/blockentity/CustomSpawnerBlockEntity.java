package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CustomSpawnerBlockEntity extends BlockEntity implements Spawner {
    protected boolean firstTick = true;

    public CustomSpawnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        return this.getSpawner().onEventTriggered(this.level, id) ? true : super.triggerEvent(id, type);
    }

    @Override
    public void setEntityId(EntityType<?> type, RandomSource random) {
        this.getSpawner().setEntityId(type, this.level, random, this.worldPosition);
        this.setChanged();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.getSpawner().load(this.level, this.worldPosition, tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.getSpawner().save(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.saveWithoutMetadata();
        tag.remove("SpawnPotentials");
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public abstract BaseSpawner getSpawner();
}
