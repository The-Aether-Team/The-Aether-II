package com.aetherteam.aetherii.world.structure.pool;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIPoolElementTypes {
    public static final DeferredRegister<StructurePoolElementType<?>> POOL_ELEMENTS = DeferredRegister.create(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, AetherII.MODID);
    public static final DeferredHolder<StructurePoolElementType<?>, StructurePoolElementType<AetherPoolElement>> AETHER = POOL_ELEMENTS.register("aether_pool_element", () -> () -> AetherPoolElement.CODEC);
}