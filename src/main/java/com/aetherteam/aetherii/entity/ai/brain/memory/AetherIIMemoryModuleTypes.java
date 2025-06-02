package com.aetherteam.aetherii.entity.ai.brain.memory;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.entity.passive.Kirrid;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

public class AetherIIMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, AetherII.MODID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> EAT_GRASS_COOLDOWN = MEMORY_MODULE_TYPES.register("eat_grass_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Kirrid>> KIRRID_BATTLE_TARGET = MEMORY_MODULE_TYPES.register("kirrid_battle_target", () -> new MemoryModuleType<>(Optional.empty()));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockPos>> TAEGORE_SEARCH_TARGET = MEMORY_MODULE_TYPES.register("taegore_search_target", () -> new MemoryModuleType<>(Optional.empty()));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> TAEGORE_DIGGING = MEMORY_MODULE_TYPES.register("taegore_digging", () -> new MemoryModuleType<>(Optional.empty()));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> TAEGORE_SEARCH_COOLDOWN = MEMORY_MODULE_TYPES.register("taegore_search_cooldown", () -> new MemoryModuleType<>(Optional.of(Unit.CODEC)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<List<GlobalPos>>> TAEGORE_EXPLORED_POSITIONS = MEMORY_MODULE_TYPES.register("taegore_explored_positions", () -> new MemoryModuleType<>(Optional.of(Codec.list(GlobalPos.CODEC))));
}
