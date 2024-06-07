package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIITreeFeatures;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIIVegetationFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class AetherIIVegetationPlacements {
    public static final ResourceKey<PlacedFeature> SHORT_GRASS_PATCH = AetherIIPlacementUtils.createKey("short_grass_patch");
    public static final ResourceKey<PlacedFeature> MEDIUM_GRASS_PATCH = AetherIIPlacementUtils.createKey("medium_grass_patch");
    public static final ResourceKey<PlacedFeature> LONG_GRASS_PATCH = AetherIIPlacementUtils.createKey("long_grass_patch");
    public static final ResourceKey<PlacedFeature> HIGHLANDS_BUSH_PATCH = AetherIIPlacementUtils.createKey("highlands_bush_patch");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH = AetherIIPlacementUtils.createKey("blueberry_bush_patch");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH = AetherIIPlacementUtils.createKey("orange_tree_patch");

    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH = AetherIIPlacementUtils.createKey("highfields_flower_patch");

    public static final ResourceKey<PlacedFeature> FLOURISHING_FIELD_TREES = AetherIIPlacementUtils.createKey("flourishing_field_trees");
    public static final ResourceKey<PlacedFeature> VERDANT_WOODS_TREES = AetherIIPlacementUtils.createKey("verdant_woods_trees");
    public static final ResourceKey<PlacedFeature> SHROUDED_FOREST_TREES = AetherIIPlacementUtils.createKey("shrouded_forest_trees");
    public static final ResourceKey<PlacedFeature> MAGNETIC_SCAR_TREES = AetherIIPlacementUtils.createKey("magnetic_scar_trees");
    public static final ResourceKey<PlacedFeature> TURQUOISE_FOREST_TREES = AetherIIPlacementUtils.createKey("turquoise_forest_trees");
    public static final ResourceKey<PlacedFeature> VIOLET_HIGHWOODS_TREES = AetherIIPlacementUtils.createKey("violet_highwoods_trees");
    public static final ResourceKey<PlacedFeature> FRIGID_SIERRA_TREES = AetherIIPlacementUtils.createKey("frigid_sierra_trees");
    public static final ResourceKey<PlacedFeature> ENDURING_WOODLAND_TREES = AetherIIPlacementUtils.createKey("enduring_woodland_trees");
    public static final ResourceKey<PlacedFeature> CONTAMINATED_JUNGLE_TREES = AetherIIPlacementUtils.createKey("contaminated_jungle_trees");
    public static final ResourceKey<PlacedFeature> BATTLEGROUND_WASTES_TREES = AetherIIPlacementUtils.createKey("battleground_wastes_trees");

    public static final ResourceKey<PlacedFeature> AETHER_GRASS_BONEMEAL = AetherIIPlacementUtils.createKey("aether_grass_bonemeal");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIPlacementUtils.register(context, SHORT_GRASS_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.SHORT_GRASS_PATCH),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, MEDIUM_GRASS_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.MEDIUM_GRASS_PATCH),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, LONG_GRASS_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.LONG_GRASS_PATCH),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, HIGHFIELDS_FLOWER_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.HIGHFIELDS_FLOWER_PATCH),
                RarityFilter.onAverageOnceEvery(2),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, HIGHLANDS_BUSH_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.HIGHLANDS_BUSH_PATCH),
                RarityFilter.onAverageOnceEvery(8),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, BLUEBERRY_BUSH_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.BLUEBERRY_BUSH_PATCH),
                RarityFilter.onAverageOnceEvery(8),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, ORANGE_TREE_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.ORANGE_TREE_PATCH),
                RarityFilter.onAverageOnceEvery(16),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, FLOURISHING_FIELD_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_FLOURISHING_FIELD),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        AetherIIPlacementUtils.register(context, VERDANT_WOODS_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_VERDANT_WOODS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(16, 0.1F, 1)));
        AetherIIPlacementUtils.register(context, SHROUDED_FOREST_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_SHROUDED_FOREST),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(24, 0.1F, 1)));

        AetherIIPlacementUtils.register(context, MAGNETIC_SCAR_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_MAGNETIC_SCAR),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        AetherIIPlacementUtils.register(context, TURQUOISE_FOREST_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_TURQUOISE_FOREST),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1F, 1)));
        AetherIIPlacementUtils.register(context, VIOLET_HIGHWOODS_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_VIOLET_HIGHWOODS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

        AetherIIPlacementUtils.register(context, FRIGID_SIERRA_TREES, configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATBOA),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(8)));
        AetherIIPlacementUtils.register(context, ENDURING_WOODLAND_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_ENDURING_WOODLANDS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

        AetherIIPlacementUtils.register(context, CONTAMINATED_JUNGLE_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_AMBEROOT_FOREST),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        AetherIIPlacementUtils.register(context, BATTLEGROUND_WASTES_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_AMBEROOT),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));

        AetherIIPlacementUtils.register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.AETHER_GRASS_BONEMEAL), PlacementUtils.isEmpty());
    }
}