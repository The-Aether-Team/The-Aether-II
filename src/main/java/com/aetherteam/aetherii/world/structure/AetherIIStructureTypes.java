package com.aetherteam.aetherii.world.structure;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherIIStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, AetherII.MODID);
    public static final DeferredHolder<StructureType<?>, StructureType<AetherJigsawStructure>> AETHER_JIGSAW = STRUCTURE_TYPES.register("aether_jigsaw", () -> () -> AetherJigsawStructure.CODEC);
}