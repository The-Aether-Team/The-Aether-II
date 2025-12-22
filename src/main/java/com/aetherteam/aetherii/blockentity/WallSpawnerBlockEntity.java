package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.entity.ConditionalSpawner;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.BaseSpawnerAccessor;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class WallSpawnerBlockEntity extends CustomSpawnerBlockEntity {
    protected boolean firstTick = true;

    private final ConditionalSpawner spawner = new ConditionalSpawner() {
        @Override
        public void broadcastEvent(Level level, BlockPos pos, int id) {
            level.blockEvent(pos, WallSpawnerBlockEntity.this.getBlockState().getBlock(), id, 0);
        }

        @Override
        public void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData data) {
            super.setNextSpawnData(level, pos, data);
            if (level != null) {
                BlockState state = level.getBlockState(pos);
                level.sendBlockUpdated(pos, state, state, 260);
            }
        }

        @Override
        public void markSyncDelay() {
            WallSpawnerBlockEntity.this.markSyncDelay();
        }

        @Override
        public Either<BlockEntity, Entity> getOwner() {
            return Either.left(WallSpawnerBlockEntity.this);
        }

        @Override
        public boolean canSpawn(ServerLevel serverLevel, BlockPos pos) {
            return WallSpawnerBlockEntity.this.getBlockState().getValue(BlockStateProperties.TRIGGERED);
        }
    };

    public WallSpawnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }


    public void markSyncDelay() {
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, WallSpawnerBlockEntity blockEntity) {
        blockEntity.getSpawner().clientTick(level, pos);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, WallSpawnerBlockEntity blockEntity) {
        if (blockEntity.firstTick) {
            BaseSpawnerAccessor accessor = (BaseSpawnerAccessor) blockEntity.getSpawner();
            accessor.aether_ii$setSpawnCount(1);
            if (blockEntity.getLevel() != null) {
                blockEntity.setPos(pos.relative(state.getValue(HorizontalDirectionalBlock.FACING)).getBottomCenter(), blockEntity.getLevel().getRandom());
            }
            blockEntity.firstTick = false;
        }
        blockEntity.getSpawner().serverTick((ServerLevel) level, pos);
    }

    public void setPos(Vec3 spawnPos, RandomSource random) {
        ((ConditionalSpawner) this.getSpawner()).setPos(spawnPos, this.level, random, this.worldPosition);
        this.setChanged();
    }

    @Override
    public BaseSpawner getSpawner() {
        return this.spawner;
    }
}
