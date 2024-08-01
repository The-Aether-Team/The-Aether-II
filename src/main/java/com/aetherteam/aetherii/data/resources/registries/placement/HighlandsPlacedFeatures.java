package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.features.HighlandsConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class HighlandsPlacedFeatures {
    // Trees
    // Highfields
    public static final ResourceKey<PlacedFeature> FLOURISHING_FIELD_TREES = createKey("flourishing_field_trees");
    public static final ResourceKey<PlacedFeature> VERDANT_WOODS_TREES = createKey("verdant_woods_trees");
    public static final ResourceKey<PlacedFeature> SHROUDED_FOREST_TREES = createKey("shrouded_forest_trees");
    public static final ResourceKey<PlacedFeature> SHIMMERING_BASIN_TREES = createKey("shimmering_basin_trees");
    public static final ResourceKey<PlacedFeature> SHIMMERING_BASIN_TREES_SUNKEN = createKey("shimmering_basin_trees_sunken");

    // Magnetic
    public static final ResourceKey<PlacedFeature> MAGNETIC_SCAR_TREES = createKey("magnetic_scar_trees");
    public static final ResourceKey<PlacedFeature> TURQUOISE_FOREST_TREES = createKey("turquoise_forest_trees");
    public static final ResourceKey<PlacedFeature> VIOLET_HIGHWOODS_TREES = createKey("violet_highwoods_trees");

    // Arctic
    public static final ResourceKey<PlacedFeature> FRIGID_SIERRA_TREES = createKey("frigid_sierra_trees");
    public static final ResourceKey<PlacedFeature> ENDURING_WOODLAND_TREES = createKey("enduring_woodland_trees");
    public static final ResourceKey<PlacedFeature> FROZEN_LAKES_TREES = createKey("frozen_lakes_trees");

    // Irradiated
    public static final ResourceKey<PlacedFeature> CONTAMINATED_JUNGLE_TREES = createKey("contaminated_jungle_trees");
    public static final ResourceKey<PlacedFeature> BATTLEGROUND_WASTES_TREES = createKey("battleground_wastes_trees");
    
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        bootstrapTrees(context);
    }

    public static void bootstrapTrees(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Highfields
        register(context, FLOURISHING_FIELD_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FLOURISHING_FIELD),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, VERDANT_WOODS_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_VERDANT_WOODS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, SHROUDED_FOREST_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_SHROUDED_FOREST),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(22, 0.1F, 1)));
        register(context, SHIMMERING_BASIN_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_SHIMMERING_BASIN),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(1)));
        register(context, SHIMMERING_BASIN_TREES_SUNKEN, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WISPROOT),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(2),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AetherIIBlocks.WISPROOT_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
        );

        // Magnetic
        register(context, MAGNETIC_SCAR_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_MAGNETIC_SCAR),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        register(context, TURQUOISE_FOREST_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_TURQUOISE_FOREST),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1)));
        register(context, VIOLET_HIGHWOODS_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_VIOLET_HIGHWOODS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

        // Arctic
        register(context, FRIGID_SIERRA_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FRIGID_SIERRA),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(16)));
        register(context, ENDURING_WOODLAND_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_ENDURING_WOODLANDS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
        register(context, FROZEN_LAKES_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FROZEN_LAKES),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(1)));

        // Irradiated
        register(context, CONTAMINATED_JUNGLE_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_IRRADIATED),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, BATTLEGROUND_WASTES_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_IRRADIATED),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
