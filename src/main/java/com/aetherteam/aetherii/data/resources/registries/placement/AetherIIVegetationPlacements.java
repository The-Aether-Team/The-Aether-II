package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIIVegetationFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class AetherIIVegetationPlacements {
    public static final ResourceKey<PlacedFeature> SHORT_GRASS_PATCH = AetherIIPlacementUtils.createKey("short_grass_patch");
    public static final ResourceKey<PlacedFeature> MEDIUM_GRASS_PATCH = AetherIIPlacementUtils.createKey("medium_grass_patch");
    public static final ResourceKey<PlacedFeature> LONG_GRASS_PATCH = AetherIIPlacementUtils.createKey("long_grass_patch");
    public static final ResourceKey<PlacedFeature> HIGHLANDS_BUSH_PATCH = AetherIIPlacementUtils.createKey("highlands_bush_patch");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH = AetherIIPlacementUtils.createKey("blueberry_bush_patch");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH = AetherIIPlacementUtils.createKey("orange_tree_patch");
    public static final ResourceKey<PlacedFeature> VALKYRIE_SPROUT_PATCH = AetherIIPlacementUtils.createKey("valkyrie_sprout_patch");

    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH_DENSE = AetherIIPlacementUtils.createKey("highfields_flower_patch_dense");
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH_SPARSE = AetherIIPlacementUtils.createKey("highfields_flower_patch_sparse");

    public static final ResourceKey<PlacedFeature> AETHER_GRASS_BONEMEAL = AetherIIPlacementUtils.createKey("aether_grass_bonemeal");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
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

        AetherIIPlacementUtils.register(context, HIGHFIELDS_FLOWER_PATCH_DENSE, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.HIGHFIELDS_FLOWER_PATCH),
                CountPlacement.of(2),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, HIGHFIELDS_FLOWER_PATCH_SPARSE, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.HIGHFIELDS_FLOWER_PATCH),
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
        AetherIIPlacementUtils.register(context, VALKYRIE_SPROUT_PATCH, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.VALKYRIE_SPROUT_PATCH),
                RarityFilter.onAverageOnceEvery(4),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());





        AetherIIPlacementUtils.register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.AETHER_GRASS_BONEMEAL), PlacementUtils.isEmpty());
    }
}