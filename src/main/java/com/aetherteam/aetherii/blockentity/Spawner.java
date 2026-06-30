package com.aetherteam.aetherii.blockentity;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;

public interface Spawner {
    void setEntityId(EntityType<?> type, RandomSource random);
}
