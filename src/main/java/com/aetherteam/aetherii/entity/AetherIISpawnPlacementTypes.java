package com.aetherteam.aetherii.entity;

import net.minecraft.world.entity.SpawnPlacementType;

public interface AetherIISpawnPlacementTypes {
    SpawnPlacementType NOT_IN_LIQUID = (level, pos, entityType) -> entityType != null && level.getWorldBorder().isWithinBounds(pos)
        ? level.getFluidState(pos).isEmpty()
        : false;
}
