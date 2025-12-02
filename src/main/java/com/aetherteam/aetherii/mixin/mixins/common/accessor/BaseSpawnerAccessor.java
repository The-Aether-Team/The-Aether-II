package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(BaseSpawner.class)
public interface BaseSpawnerAccessor {
    @Accessor("spawnDelay")
    void aether_ii$setSpawnDelay(int delay);

    @Accessor("minSpawnDelay")
    void aether_ii$setMinSpawnDelay(int delay);

    @Accessor("maxSpawnDelay")
    void aether_ii$setMaxSpawnDelay(int delay);

    @Accessor("spawnCount")
    void aether_ii$setSpawnCount(int count);

    @Invoker
    SpawnData callGetOrCreateNextSpawnData(@Nullable Level level, RandomSource random, BlockPos pos);
}
