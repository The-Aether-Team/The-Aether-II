package com.aetherteam.aetherii.world.structure.pool;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIPoolElementTypes {
    public static final DeferredRegister<StructurePoolElementType<?>> POOL_ELEMENTS = DeferredRegister.create(Registries.STRUCTURE_POOL_ELEMENT, AetherII.MODID);
    public static final RegistryObject<StructurePoolElementType<AetherPoolElement>> AETHER = POOL_ELEMENTS.register("aether_pool_element", () -> () -> AetherPoolElement.CODEC);
}
