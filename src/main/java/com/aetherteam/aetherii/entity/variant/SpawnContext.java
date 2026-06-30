package com.aetherteam.aetherii.entity.variant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;

public record SpawnContext(ServerLevelAccessor level, BlockPos pos) {
    public static SpawnContext create(ServerLevelAccessor level, BlockPos pos) {
        return new SpawnContext(level, pos);
    }
}
