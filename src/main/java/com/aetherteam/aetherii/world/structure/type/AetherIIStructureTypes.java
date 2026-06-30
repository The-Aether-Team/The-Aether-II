package com.aetherteam.aetherii.world.structure.type;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class AetherIIStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, AetherII.MODID);
    public static final RegistryObject<StructureType<AetherJigsawStructure>> AETHER_JIGSAW = STRUCTURE_TYPES.register("aether_jigsaw", () -> () -> AetherJigsawStructure.CODEC);
    public static final RegistryObject<StructureType<SentryRuinsStructure>> SENTRY_RUINS = STRUCTURE_TYPES.register("sentry_ruins", () -> () -> SentryRuinsStructure.CODEC.codec());
}
