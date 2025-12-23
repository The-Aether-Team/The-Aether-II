package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.block.dungeon.SentrySpawnerBlock;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BaseSpawnerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;
import java.util.List;

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
        blockEntity.active = state.getValue(SentrySpawnerBlock.TRIGGERED);

        if (this.isActive()) {
            --this.delaySyncTick;
        }

        if (this.delaySyncTick < 30 && !this.triggerPiston && this.isActive()) {
            this.triggerPiston = true;
            this.triggerTick = 50 + (29 - this.delaySyncTick);
            level.playSound(null, blockPos, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 1.25F, 1.5F);
            this.markSyncDelay();
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
        this.markSyncDelay();
    }

    public float getPistonAnimationScale(float partialTick) {
        return Mth.lerp(partialTick, this.pistonScaleOld, this.pistonScale);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        WallSpawnerBlockEntity.clientTick(level, pos, state, blockEntity);
        blockEntity.spawnerTriggerTick(level, pos, state, blockEntity);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SentrySpawnerBlockEntity blockEntity) {
        BaseSpawnerAccessor accessor = (BaseSpawnerAccessor) blockEntity.getSpawner();
        blockEntity.getSpawner().serverTick((ServerLevel) level, pos);
        blockEntity.spawnerTriggerTick(level, pos, state, blockEntity);
        if (blockEntity.firstTick || (blockEntity.triggerPiston && blockEntity.triggerTick == 0)) {
            if (blockEntity.getLevel() != null) {
                boolean hasPosition = false;
                List<Direction> directions = Direction.Plane.HORIZONTAL.shuffledCopy(level.getRandom());
                for (Direction randomDirection : directions) {
                    BlockPos relativePos = pos.relative(randomDirection);
                    if (level.isEmptyBlock(relativePos) && !hasPosition) {
                        blockEntity.setPos(relativePos.getBottomCenter(), blockEntity.getLevel().getRandom());
                        hasPosition = true;
                    }
                }
            }
            accessor.aether_ii$setSpawnCount(1);
            if (blockEntity.firstTick) {
                blockEntity.firstTick = false;
            }
        }
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
