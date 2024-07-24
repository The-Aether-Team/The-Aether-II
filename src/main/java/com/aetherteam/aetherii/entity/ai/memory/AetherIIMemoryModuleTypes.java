package com.aetherteam.aetherii.entity.ai.memory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class AetherIIMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, AetherII.MODID);
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Kirrid>> KIRRID_BATTLE_TARGET = MEMORY_MODULE_TYPES.register("kirrid_battle_target", () -> new MemoryModuleType<>(Optional.empty()));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> EAT_GRASS_COOLDOWN = MEMORY_MODULE_TYPES.register("eat_grass_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
}
