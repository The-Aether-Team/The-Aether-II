package com.aetherteam.aetherii.entity.variant.spawning;

import com.mojang.serialization.MapCodec;

public class AetherIISpawnConditions {
    public static final MapCodec<BiomeCheck> BIOME = BiomeCheck.MAP_CODEC;
    public static final MapCodec<LightCheck> LIGHT = LightCheck.MAP_CODEC;
    public static final MapCodec<RandomCheck> RANDOM = RandomCheck.MAP_CODEC;
}
