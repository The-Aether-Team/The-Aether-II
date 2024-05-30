package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.data.resources.registries.features.AetherIIVegetationFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AetherIIVegetationPlacements {
    public static final ResourceKey<PlacedFeature> FLOURISHING_FIELD_TREES = AetherIIPlacementUtils.createKey("flourishing_field_trees");
    public static final ResourceKey<PlacedFeature> VERDANT_WOODS_TREES = AetherIIPlacementUtils.createKey("verdant_woods_trees");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIPlacementUtils.register(context, FLOURISHING_FIELD_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_FLOURISHING_FIELD),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

        AetherIIPlacementUtils.register(context, VERDANT_WOODS_TREES, configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_VERDANT_WOODS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(20, 0.1F, 1)));
    }
}