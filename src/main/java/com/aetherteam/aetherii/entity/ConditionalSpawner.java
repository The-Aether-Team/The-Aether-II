package com.aetherteam.aetherii.entity;

import com.aetherteam.aetherii.mixin.mixins.common.accessor.BaseSpawnerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class ConditionalSpawner extends BaseSpawner {
    @Override
    public void serverTick(ServerLevel serverLevel, BlockPos pos) {
        if (this.canSpawn(serverLevel, pos)) {
            super.serverTick(serverLevel, pos);
        }
    }

    public void setPos(Vec3 spawnPos, @Nullable Level level, RandomSource random, BlockPos pos) {
        ((BaseSpawnerAccessor) this).callGetOrCreateNextSpawnData(level, random, pos).getEntityToSpawn().store("Pos", Vec3.CODEC, spawnPos);
    }

    public void markSyncDelay() {
    }

    public abstract boolean canSpawn(ServerLevel serverLevel, BlockPos pos);
}
