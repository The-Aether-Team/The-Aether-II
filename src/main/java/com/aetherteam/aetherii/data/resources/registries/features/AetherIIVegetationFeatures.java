package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.OrangeTreeBlock;
import com.aetherteam.aetherii.block.natural.ValkyrieSproutBlock;
import com.aetherteam.aetherii.data.resources.builders.AetherIIFeatureBuilders;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenConfiguredFeatureBuilders;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;

public class AetherIIVegetationFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_GRASS_PATCH = AetherIIFeatureUtils.registerKey("short_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_GRASS_PATCH = AetherIIFeatureUtils.registerKey("medium_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LONG_GRASS_PATCH = AetherIIFeatureUtils.registerKey("long_grass_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHFIELDS_FLOWER_PATCH = AetherIIFeatureUtils.registerKey("highfields_flower_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHLANDS_BUSH_PATCH = AetherIIFeatureUtils.registerKey("highlands_bush_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBERRY_BUSH_PATCH = AetherIIFeatureUtils.registerKey("blueberry_bush_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_TREE_PATCH = AetherIIFeatureUtils.registerKey("orange_tree_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRETTL_PLANT_PATCH = AetherIIFeatureUtils.registerKey("brettl_plant_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VALKYRIE_SPROUT_PATCH = AetherIIFeatureUtils.registerKey("valkyrie_sprout_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_PILLAR_TURF_TOP = AetherIIFeatureUtils.registerKey("ferrosite_pillar_turf_top");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_PILLAR_TURF = AetherIIFeatureUtils.registerKey("ferrosite_pillar_turf");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOURISHING_FIELD = AetherIIFeatureUtils.registerKey("trees_flourishing_field");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_VERDANT_WOODS = AetherIIFeatureUtils.registerKey("trees_verdant_woods");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SHROUDED_FOREST = AetherIIFeatureUtils.registerKey("trees_shrouded_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_MAGNETIC_SCAR = AetherIIFeatureUtils.registerKey("trees_magnetic_scar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TURQUOISE_FOREST = AetherIIFeatureUtils.registerKey("trees_turquoise_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_VIOLET_HIGHWOODS = AetherIIFeatureUtils.registerKey("trees_violet_highwoods");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_ENDURING_WOODLANDS = AetherIIFeatureUtils.registerKey("trees_enduring_woodland");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_IRRADIATED = AetherIIFeatureUtils.registerKey("trees_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AMBEROOT_SPARSE = AetherIIFeatureUtils.registerKey("trees_amberoot_sparse");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AMBEROOT_DENSE = AetherIIFeatureUtils.registerKey("trees_amberoot_dense");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_GRASS_BONEMEAL = AetherIIFeatureUtils.registerKey("aether_grass_bonemeal");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        SimpleWeightedRandomList.Builder<BlockState> orangeTrees = new SimpleWeightedRandomList.Builder<>();
        orangeTrees.add(AetherIIBlocks.ORANGE_TREE.get().defaultBlockState().setValue(OrangeTreeBlock.AGE, 3), 1);
        orangeTrees.add(AetherIIBlocks.ORANGE_TREE.get().defaultBlockState().setValue(OrangeTreeBlock.AGE, 4), 1);

        AetherIIFeatureUtils.register(context, SHORT_GRASS_PATCH, Feature.RANDOM_PATCH, AetherIIFeatureBuilders.aetherGrassPatch(BlockStateProvider.simple(AetherIIBlocks.AETHER_SHORT_GRASS.get()), 64));
        AetherIIFeatureUtils.register(context, MEDIUM_GRASS_PATCH, Feature.RANDOM_PATCH, AetherIIFeatureBuilders.aetherGrassPatch(BlockStateProvider.simple(AetherIIBlocks.AETHER_MEDIUM_GRASS.get()), 32));
        AetherIIFeatureUtils.register(context, LONG_GRASS_PATCH, Feature.RANDOM_PATCH, AetherIIFeatureBuilders.aetherGrassPatch(BlockStateProvider.simple(AetherIIBlocks.AETHER_LONG_GRASS.get()), 16));

        AetherIIFeatureUtils.register(context, HIGHFIELDS_FLOWER_PATCH, Feature.FLOWER,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherIIBlocks.HESPEROSE.get().defaultBlockState(), 1).add(AetherIIBlocks.TARABLOOM.get().defaultBlockState(), 1)), 32));

        AetherIIFeatureUtils.register(context, HIGHLANDS_BUSH_PATCH, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AetherIIBlocks.HIGHLANDS_BUSH.get())), List.of(AetherIIBlocks.AETHER_GRASS_BLOCK.get())));
        AetherIIFeatureUtils.register(context, BLUEBERRY_BUSH_PATCH, Feature.RANDOM_PATCH,
                NitrogenConfiguredFeatureBuilders.grassPatch(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(AetherIIBlocks.BLUEBERRY_BUSH.get().defaultBlockState(), 1)), 64));
        AetherIIFeatureUtils.register(context, ORANGE_TREE_PATCH, Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(16, PlacementUtils.onlyWhenEmpty(AetherIIFeatures.ORANGE_TREE.get(), new SimpleBlockConfiguration(new WeightedStateProvider(orangeTrees)))));
        AetherIIFeatureUtils.register(context, VALKYRIE_SPROUT_PATCH, Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(16, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AetherIIBlocks.VALKYRIE_SPROUT.get().defaultBlockState().setValue(ValkyrieSproutBlock.AGE, 2))))));
        AetherIIFeatureUtils.register(context, BRETTL_PLANT_PATCH, Feature.RANDOM_PATCH,
                AetherIIFeatureBuilders.brettlPatch(64, PlacementUtils.onlyWhenEmpty(AetherIIFeatures.BRETTL_PLANT.get(), new NoneFeatureConfiguration())));

        AetherIIFeatureUtils.register(context, FERROSITE_PILLAR_TURF_TOP, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.AETHER_DIRT,
                        BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AETHER_GRASS_BONEMEAL)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        16,
                        0.0F,
                        UniformInt.of(24, 28),
                        0.3F
                )
        );

        AetherIIFeatureUtils.register(context, FERROSITE_PILLAR_TURF, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.FERROSITE,
                        BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(FERROSITE_PILLAR_TURF_TOP)),
                        CaveSurface.FLOOR,
                        UniformInt.of(3, 4),
                        0.0F,
                        16,
                        1.0F,
                        UniformInt.of(24, 28),
                        0.3F
                )
        );

        AetherIIFeatureUtils.register(context, TREES_FLOURISHING_FIELD, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.2F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_VERDANT_WOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.6F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.1F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIIVegetationFeatures.TREES_AMBEROOT_DENSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.05F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.NEST_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.015F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_SHROUDED_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.05F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYPLANE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_MAGNETIC_SCAR, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.35F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.01F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYBIRCH), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_TURQUOISE_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.03F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYBIRCH), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_VIOLET_HIGHWOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.01F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_ENDURING_WOODLANDS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATBOA), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATBOA_SAPLING.get())), 0.03F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYPINE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPINE_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_IRRADIATED, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_SKYROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.4F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYPLANE_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get())), 0.05F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATOAK_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.002F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYBIRCH_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get())), 0.075F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.WISPTOP_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.0075F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYPINE_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPINE_SAPLING.get())), 0.125F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.GREATBOA_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATBOA_SAPLING.get())), 0.001F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SINGULAR_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.065F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SKYROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_AMBEROOT_SPARSE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SINGULAR_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.2F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, TREES_AMBEROOT_DENSE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.SINGULAR_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.2F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AetherIITreeFeatures.LARGE_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get()))));

        AetherIIFeatureUtils.register(context, AETHER_GRASS_BONEMEAL, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 1)
                .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 1)
                .add(AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 1)
        )));
    }
}