package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.world.tree.decorator.WisprootTreeDecorator;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.AmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.LargeAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.SingularAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatoakFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatrootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.skyroot.*;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisprootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisptopFoliagePlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class HighlandsConfiguredFeatures { //todo maybe sort these by biome first instead idk.
    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_FIELD = AetherIIFeatureUtils.registerKey("grass_field");


    // Trees
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBEROOT = createKey("amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_AMBEROOT = createKey("large_amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SINGULAR_AMBEROOT = createKey("singular_amberoot");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AMBEROOT_SPARSE = createKey("trees_amberoot_sparse");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AMBEROOT_DENSE = createKey("trees_amberoot_dense");

    // Highfields
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT = createKey("skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_SKYROOT = createKey("short_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT = createKey("large_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_SKYROOT = createKey("nest_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPLANE = createKey("skyplane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_SKYPLANE = createKey("short_skyplane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT = createKey("wisproot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATOAK = createKey("greatoak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_GREATOAK = createKey("short_greatoak");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_FLOURISHING_FIELD = createKey("trees_biome_flourishing_field");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_VERDANT_WOODS = createKey("trees_biome_verdant_woods");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_SHROUDED_FOREST = createKey("trees_biome_shrouded_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_SHIMMERING_BASIN = createKey("trees_biome_shimmering_basin");

    // Magnetic
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYBIRCH = createKey("skybirch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP = createKey("wisptop");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATROOT = createKey("greatroot");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_MAGNETIC_SCAR = createKey("trees_biome_magnetic_scar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_TURQUOISE_FOREST = createKey("trees_biome_turquoise_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_VIOLET_HIGHWOODS = createKey("trees_biome_violet_highwoods");

    // Arctic
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPINE = createKey("skypine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATBOA = createKey("greatboa");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_FRIGID_SIERRA = createKey("trees_biome_frigid_sierra");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_ENDURING_WOODLANDS = createKey("trees_biome_enduring_woodland");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_FROZEN_LAKES = createKey("trees_biome_frozen_lakes");

    // Irradiated
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_IRRADIATED = createKey("skyroot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT_IRRADIATED = createKey("large_skyroot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPLANE_IRRADIATED = createKey("skyplane_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYBIRCH_IRRADIATED = createKey("skybirch_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPINE_IRRADIATED = createKey("skypine_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT_IRRADIATED = createKey("wisproot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP_IRRADIATED = createKey("wisptop_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATROOT_IRRADIATED = createKey("greatroot_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATOAK_IRRADIATED = createKey("greatoak_irradiated");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATBOA_IRRADIATED = createKey("greatboa_irradiated");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_IRRADIATED = createKey("trees_irradiated");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        bootstrapVegetation(context);
        bootstrapTrees(context);
    }

    private static void bootstrapVegetation(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, GRASS_FIELD, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                80,
                12,
                4,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new NoiseProvider(
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.02F,
                                        List.of(
                                                AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState()
                                        )
                                )
                        )
                )
        ));
    }

    private static void bootstrapTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        
        register(context, AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(5, 4, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new AmberootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, LARGE_AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(8, 5, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new LargeAmberootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, SINGULAR_AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(6, 4, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new SingularAmberootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());

        register(context, TREES_AMBEROOT_SPARSE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SINGULAR_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.2F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get()))));
        register(context, TREES_AMBEROOT_DENSE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SINGULAR_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.2F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get()))));

        // Highfields
        register(context, SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 1), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());
        register(context, SHORT_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());
        register(context, LARGE_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(7, 6, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new LargeSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, NEST_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(12, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new NestSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, SKYPLANE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 2), BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, SHORT_SKYPLANE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, WISPROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());
        register(context, GREATOAK, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(13, 5, 6), BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());
        register(context, SHORT_GREATOAK, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(13, 4, 3), BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        register(context, TREES_BIOME_FLOURISHING_FIELD, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.1F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_SKYPLANE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.2F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.0075F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));
        register(context, TREES_BIOME_VERDANT_WOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.6F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(NEST_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPLANE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get())), 0.01F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.1F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_DENSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.05F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));
        register(context, TREES_BIOME_SHROUDED_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(NEST_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.0015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.15F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.01F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPLANE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get()))));
        register(context, TREES_BIOME_SHIMMERING_BASIN, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.6F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.3F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_SKYPLANE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get())), 0.05F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));

        // Magnetic
        register(context, SKYBIRCH, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(8, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYBIRCH_LEAVES.get().defaultBlockState()),
                        new SkybirchFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, WISPTOP, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());
        register(context, GREATROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(6, 2, 5), BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LEAVES.get().defaultBlockState()),
                        new GreatrootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        register(context, TREES_BIOME_MAGNETIC_SCAR, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.35F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.01F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.02F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYBIRCH), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get()))));
        register(context, TREES_BIOME_TURQUOISE_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.0075F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.05F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_DENSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.005F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYBIRCH), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get()))));
        register(context, TREES_BIOME_VIOLET_HIGHWOODS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYBIRCH), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.002F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.001F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get()))));

        // Arctic
        register(context, SKYPINE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYPINE_LEAVES.get().defaultBlockState()),
                        new SkypineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, GREATBOA, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(15, 3, 8), BlockStateProvider.simple(AetherIIBlocks.GREATBOA_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        register(context, TREES_BIOME_FRIGID_SIERRA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPINE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPINE_SAPLING.get())), 0.25F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATBOA), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATBOA_SAPLING.get()))));
        register(context, TREES_BIOME_ENDURING_WOODLANDS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATBOA), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATBOA_SAPLING.get())), 0.03F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPINE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPINE_SAPLING.get()))));
        register(context, TREES_BIOME_FROZEN_LAKES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATBOA), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATBOA_SAPLING.get())), 0.35F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPINE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPINE_SAPLING.get()))));

        // Irradiated
        register(context, SKYROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());
        register(context, LARGE_SKYROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(7, 6, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get().defaultBlockState()),
                        new LargeSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, SKYPLANE_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, SKYBIRCH_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(8, 3, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get().defaultBlockState()),
                        new SkybirchFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, SKYPINE_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 5, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get().defaultBlockState()),
                        new SkypineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().build());
        register(context, WISPROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());
        register(context, WISPTOP_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());
        register(context, GREATROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(6, 2, 5), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get().defaultBlockState()),
                        new GreatrootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());
        register(context, GREATOAK_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(13, 5, 6), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());
        register(context, GREATBOA_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(15, 3, 8), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(0, 0, 0))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());

        register(context, TREES_IRRADIATED, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_SKYROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.4F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPLANE_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPLANE_SAPLING.get())), 0.05F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATOAK_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.002F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYBIRCH_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get())), 0.075F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPTOP_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.0075F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPINE_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYPINE_SAPLING.get())), 0.125F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATBOA_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATBOA_SAPLING.get())), 0.001F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SINGULAR_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(LARGE_AMBEROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.065F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT_IRRADIATED), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AetherII.MODID, name));
    }

    private static void register(BootstrapContext<ConfiguredFeature<?, ?>> BootstrapContext, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, Feature<NoneFeatureConfiguration> feature) {
        register(BootstrapContext, resourceKey, feature, FeatureConfiguration.NONE);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
