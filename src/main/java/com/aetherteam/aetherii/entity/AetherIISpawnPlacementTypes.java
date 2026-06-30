package com.aetherteam.aetherii.entity;

import net.minecraft.world.entity.SpawnPlacements;

public interface AetherIISpawnPlacementTypes {
    SpawnPlacements.Type NOT_IN_LIQUID = SpawnPlacements.Type.create("AETHER_II_NOT_IN_LIQUID", (level, pos, entityType) -> entityType != null && level.getWorldBorder().isWithinBounds(pos)
        ? level.getFluidState(pos).isEmpty()
        : false);
}
