package com.aetherteam.aetherii.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class CustomSpawnerBlockEntity extends BlockEntity implements Spawner {
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
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.getSpawner().load(this.level, this.worldPosition, input);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        this.getSpawner().save(output);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = this.saveCustomOnly(provider);
        tag.remove("SpawnPotentials");
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public abstract BaseSpawner getSpawner();
}
