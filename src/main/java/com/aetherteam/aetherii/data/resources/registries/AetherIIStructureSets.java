package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

public class AetherIIStructureSets {
    public static final ResourceKey<StructureSet> OUTPOSTS = createKey("outposts");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(AetherII.MODID, name));
    }

    /**
     * Warning for "deprecation" is suppressed because using {@link StructurePlacement.ExclusionZone} is necessary.
     */
    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(OUTPOSTS, new StructureSet(structures.getOrThrow(AetherIIStructures.OUTPOST),
                new RandomSpreadStructurePlacement(32, 20, RandomSpreadType.LINEAR, 2738116)));
    }
}