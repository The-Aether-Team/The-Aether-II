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
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH_DENSE = AetherIIPlacementUtils.createKey("highfields_flower_patch_dense");
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH_SPARSE = AetherIIPlacementUtils.createKey("highfields_flower_patch_sparse");

    public static final ResourceKey<PlacedFeature> AETHER_GRASS_BONEMEAL = AetherIIPlacementUtils.createKey("aether_grass_bonemeal");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIPlacementUtils.register(context, HIGHFIELDS_FLOWER_PATCH_DENSE, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.HIGHFIELDS_FLOWER_PATCH),
                CountPlacement.of(2),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, HIGHFIELDS_FLOWER_PATCH_SPARSE, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.HIGHFIELDS_FLOWER_PATCH),
                RarityFilter.onAverageOnceEvery(2),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BiomeFilter.biome());





        AetherIIPlacementUtils.register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.AETHER_GRASS_BONEMEAL), PlacementUtils.isEmpty());
    }
}