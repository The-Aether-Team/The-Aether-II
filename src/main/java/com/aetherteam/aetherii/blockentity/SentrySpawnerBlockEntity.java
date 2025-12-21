package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.dungeon.SentryWallSpawnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;

public class SentrySpawnerBlockEntity extends WallSpawnerBlockEntity {
    public boolean triggerPiston;
    private int triggerTick;
    private int delaySyncTick;
    private float pistonScale;
    private float pistonScaleOld;
    private boolean active;

    public SentrySpawnerBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherIIBlockEntityTypes.SENTRY_SPAWNER.get(), pos, blockState);
    }

    @Override
    public void markSyncDelay() {
        super.markSyncDelay();
        //BaseSpawner's Delay thing's client and server are different. so bring server side valve and use it
        //make sync from spawner server side
        this.delaySyncTick = this.getSpawner().spawnDelay;
        this.markUpdated();
    }

    public void spawnerTriggerTick(Level level, BlockPos blockPos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        blockEntity.active = state.getValue(SentryWallSpawnerBlock.TRIGGERED);

        if (this.isActive()) {
            --this.delaySyncTick;
        }

        if (this.delaySyncTick < 30 && !this.triggerPiston && this.isActive()) {
            this.triggerPiston = true;
            this.triggerTick = 50 + (29 - this.delaySyncTick);
            level.playSound(null, blockPos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1.25F, 1.5F);
            this.markUpdated();
        }

        if (this.triggerPiston) {
            if (this.triggerTick <= 0) {
                this.spawnTriggerStop(level, blockPos);
            } else {
                --this.triggerTick;
            }
        }
        this.pistonScaleOld = this.pistonScale;
        if (this.triggerPiston) {
            this.pistonScale = Mth.clamp(this.pistonScale + 0.1F, 0.0F, 1.0F);
        } else {
            this.pistonScale = Mth.clamp(this.pistonScale - 0.1F, 0.0F, 1.0F);
        }

        /*if (this.isDirty) {
            level.sendBlockUpdated(blockPos, state, state, 2);
        }*/
    }

    public void spawnTriggerStop(Level level, BlockPos blockPos) {
        this.triggerPiston = false;
        level.playSound(null, blockPos, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 1.25F, 1.5F);
        this.markUpdated();
    }

    public float getPistonAnimationScale(float partialTick) {
        return Mth.lerp(partialTick, this.pistonScaleOld, this.pistonScale);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        WallSpawnerBlockEntity.clientTick(level, pos, state, blockEntity);
        blockEntity.spawnerTriggerTick(level, pos, state, blockEntity);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        WallSpawnerBlockEntity.serverTick(level, pos, state, blockEntity);
        blockEntity.spawnerTriggerTick(level, pos, state, blockEntity);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (this.level != null) {
            this.markSyncDelay();
        }
    }

    public void markUpdated() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = this.saveCustomOnly(provider);
        tag.remove("SpawnPotentials");
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    public boolean isActive() {
        return active;
    }
}
