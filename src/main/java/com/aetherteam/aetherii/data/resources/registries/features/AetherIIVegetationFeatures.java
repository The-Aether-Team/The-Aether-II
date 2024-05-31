package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenConfiguredFeatureBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.List;

public class AetherIIVegetationFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHLANDS_BUSH_PATCH = AetherIIFeatureUtils.registerKey("highlands_bush_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBERRY_BUSH_PATCH = AetherIIFeatureUtils.registerKey("blueberry_bush_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOURISHING_FIELD = AetherIIFeatureUtils.registerKey("trees_flourishing_field");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_VERDANT_WOODS = AetherIIFeatureUtils.registerKey("trees_verdant_woods");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_VIOLET_HIGHWOODS = AetherIIFeatureUtils.registerKey("trees_violet_highwoods");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIFeatureUtils.register(context, HIGHLANDS_BUSH_PATCH, Feature.RANDOM_PATCH,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherIIBlocks.HIGHLANDS_BUSH.get().defaultBlockState(), 1)), 32));
        AetherIIFeatureUtils.register(context, BLUEBERRY_BUSH_PATCH, Feature.RANDOM_PATCH,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherIIBlocks.BLUEBERRY_BUSH.get().defaultBlockState(), 1)), 64));

        AetherIIFeatureUtils.register(context, TREES_FLOURISHING_FIELD, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.2F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_VERDANT_WOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.6F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.1F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.05F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_VIOLET_HIGHWOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.01F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get()))));
    }
}