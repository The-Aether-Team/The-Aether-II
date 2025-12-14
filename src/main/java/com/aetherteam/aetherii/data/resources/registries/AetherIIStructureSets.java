package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.List;
import java.util.Optional;

public class AetherIIStructureSets {
    public static final ResourceKey<StructureSet> OUTPOSTS = createKey("outposts");
    public static final ResourceKey<StructureSet> CAMPS = createKey("camps");
    public static final ResourceKey<StructureSet> SENTRY_WORKSHOP = createKey("sentry_workshop");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    /**
     * Warning for "deprecation" is suppressed because using {@link StructurePlacement.ExclusionZone} is necessary.
     */
    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        Holder.Reference<StructureSet> outposts = context.register(OUTPOSTS, new StructureSet(structures.getOrThrow(AetherIIStructures.OUTPOST),
                new RandomSpreadStructurePlacement(28, 18, RandomSpreadType.LINEAR, 2738116)));

        context.register(CAMPS, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.CAMP_HIGHFIELDS)),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.CAMP_MAGNETIC)),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.CAMP_ARCTIC))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1, 0.6F, 1147092, Optional.of(new StructurePlacement.ExclusionZone(outposts, 8)), 22, 10, RandomSpreadType.LINEAR))
        );

        context.register(SENTRY_WORKSHOP, new StructureSet(structures.getOrThrow(AetherIIStructures.SENTRY_WORKSHOP),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, 32146754, Optional.empty(), 14, 7, RandomSpreadType.TRIANGULAR)));
    }
}