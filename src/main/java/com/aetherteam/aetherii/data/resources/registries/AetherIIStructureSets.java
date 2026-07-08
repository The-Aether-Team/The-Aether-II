package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.List;
import java.util.Optional;

public class AetherIIStructureSets {
    public static final ResourceKey<StructureSet> OUTPOSTS = createKey("outposts");
    public static final ResourceKey<StructureSet> ANIMAL_DENS = createKey("animal_dens");
    public static final ResourceKey<StructureSet> AETHER_SURFACE_STRUCTURES = createKey("aether_surface_structures");
    public static final ResourceKey<StructureSet> AETHER_SURFACE_RUINS = createKey("aether_surface_ruins");
    public static final ResourceKey<StructureSet> AETHER_UNDERGROUND_RUINS = createKey("aether_underground_ruins");
    public static final ResourceKey<StructureSet> AETHER_UNDERGROUND_COMPLEXES = createKey("aether_underground_complexes");
    public static final ResourceKey<StructureSet> AQUEDUCTS = createKey("aqueducts");
    public static final ResourceKey<StructureSet> IRRADIATED_REMNANTS = createKey("irradiated_remnants");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    /**
     * Warning for "deprecation" is suppressed because using {@link StructurePlacement.ExclusionZone} is necessary.
     */
    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        Holder.Reference<StructureSet> outposts = context.register(OUTPOSTS, new StructureSet(structures.getOrThrow(AetherIIStructures.OUTPOST),
                new RandomSpreadStructurePlacement(28, 18, RandomSpreadType.LINEAR, 2738116)));

        context.register(ANIMAL_DENS, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.ANIMAL_DEN))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1, 1.0F, 4027017, Optional.of(new StructurePlacement.ExclusionZone(outposts, 6)), 8, 5, RandomSpreadType.LINEAR))
        );

        context.register(AETHER_SURFACE_STRUCTURES, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.CAMP_HIGHFIELDS), 3),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.CAMP_MAGNETIC), 3),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.CAMP_ARCTIC), 3),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.WATCHTOWER), 2),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.VERADEXIAN_RUINS_TEMPERATE), 2),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.VERADEXIAN_RUINS_ARCTIC), 2),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.ANCIENT_HENGE), 3)),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1, 1.0F, 1147092, Optional.of(new StructurePlacement.ExclusionZone(outposts, 8)), 14, 7, RandomSpreadType.LINEAR))
        );

        context.register(AETHER_SURFACE_RUINS, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.VERADEXIAN_LIBRARY_TEMPERATE), 2),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.VERADEXIAN_LIBRARY_ARCTIC), 2)),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_2, 1.0F, 2038911, Optional.of(new StructurePlacement.ExclusionZone(outposts, 6)), 20, 8, RandomSpreadType.LINEAR))
        );

        context.register(AETHER_UNDERGROUND_RUINS, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.BREXALLEN_RUINS))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, 7881032, Optional.empty(), 4, 2, RandomSpreadType.LINEAR))
        );

        context.register(AQUEDUCTS, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.VERADEXIAN_AQUEDUCT))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_2, 1.0F, 2038911, Optional.of(new StructurePlacement.ExclusionZone(outposts, 6)), 10, 5, RandomSpreadType.LINEAR))
        );

        Holder.Reference<StructureSet> sentryRuins = context.register(AETHER_UNDERGROUND_COMPLEXES, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.SENTRY_RUINS), 2),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.UNDERCLOUD_MINESHAFT), 3)),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, 32146754, Optional.empty(), 12, 6, RandomSpreadType.TRIANGULAR))
        );

        context.register(IRRADIATED_REMNANTS, new StructureSet(List.of(
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.IRRADIATED_BUNKER_REMNANTS)),
                StructureSet.entry(structures.getOrThrow(AetherIIStructures.IRRADIATED_SETTLEMENT_REMNANTS))),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.LEGACY_TYPE_1, 1.0F, 833294, Optional.of(new StructurePlacement.ExclusionZone(sentryRuins, 4)), 8, 3, RandomSpreadType.LINEAR))
        );
    }
}