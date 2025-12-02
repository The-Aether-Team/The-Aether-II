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
    private boolean spawnedEntity;

    @Override
    public void serverTick(ServerLevel serverLevel, BlockPos pos) {
        if (this.canSpawn(serverLevel, pos)) {
            super.serverTick(serverLevel, pos);
        }
    }

    public void setPos(Vec3 spawnPos, @Nullable Level level, RandomSource random, BlockPos pos) {
        ((BaseSpawnerAccessor) this).callGetOrCreateNextSpawnData(level, random, pos).getEntityToSpawn().store("Pos", Vec3.CODEC, spawnPos);
    }

    public boolean hasSpawnedEntity() {
        return this.spawnedEntity;
    }

    public void setSpawnedEntity(boolean spawnedEntity) {
        this.spawnedEntity = spawnedEntity;
    }

    public abstract boolean canSpawn(ServerLevel serverLevel, BlockPos pos);

    @Override
    public void load(@Nullable Level level, BlockPos pos, ValueInput input) {
        super.load(level, pos, input);
        this.spawnedEntity = input.getBooleanOr("SpawnedEntity", false);
    }

    @Override
    public void save(ValueOutput output) {
        super.save(output);
        output.putBoolean("SpawnedEntity", this.hasSpawnedEntity());
    }
}
