package com.aetherteam.aetherii.entity.variant.spawning;

import com.aetherteam.aetherii.AetherII;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIISpawnConditions {
    public static final DeferredRegister<MapCodec<? extends SpawnCondition>> SPAWN_CONDITION_TYPES = DeferredRegister.create(Registries.SPAWN_CONDITION_TYPE, AetherII.MODID);

    public static DeferredHolder<MapCodec<? extends SpawnCondition>, MapCodec<LightCheck>> LIGHT = SPAWN_CONDITION_TYPES.register("light", () -> LightCheck.MAP_CODEC);
    public static DeferredHolder<MapCodec<? extends SpawnCondition>, MapCodec<RandomCheck>> RANDOM = SPAWN_CONDITION_TYPES.register("random", () -> RandomCheck.MAP_CODEC);
}
