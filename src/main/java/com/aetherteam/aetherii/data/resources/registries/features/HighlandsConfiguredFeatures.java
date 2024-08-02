package com.aetherteam.aetherii.data.resources.registries.features;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.OrangeTreeBlock;
import com.aetherteam.aetherii.block.natural.ValkyrieSproutBlock;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.CoastFeature;
import com.aetherteam.aetherii.world.feature.configuration.*;
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
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;

public class HighlandsConfiguredFeatures {
    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_FIELD = createKey("grass_field");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GRASS_PATCH = createKey("small_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_GRASS_PATCH = createKey("medium_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GRASS_PATCH = createKey("large_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VALKYRIE_SPROUT_PATCH = createKey("valkyrie_sprout_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHLANDS_BUSH = createKey("highlands_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBERRY_BUSH = createKey("blueberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_TREE = createKey("orange_tree_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_GRASS_BONEMEAL = AetherIIFeatureUtils.registerKey("aether_grass_bonemeal");


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
    
    
    // Worldgen
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_QUICKSOIL = createKey("coast_quicksoil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_FERROSITE_SAND = createKey("coast_ferrosite_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_ARCTIC_PACKED_ICE = createKey("coast_arctic_packed_ice");

    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_POND = createKey("water_pond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_SPRING = createKey("water_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISE_LAKE = createKey("noise_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NOISE_LAKE_ARCTIC = createKey("noise_lake_arctic");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_PILLAR = createKey("ferrosite_pillar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_PILLAR_TURF_TOP = createKey("ferrosite_pillar_turf_top");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_PILLAR_TURF = createKey("ferrosite_pillar_turf");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FERROSITE_SPIKE = createKey("ferrosite_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCTIC_ICE_SPIKE = createKey("arctic_ice_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_ARCTIC_ICE_SPIKE = createKey("mega_arctic_ice_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCTIC_ICE_SPIKE_VARIANTS = createKey("arctic_ice_spike_variants");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FREEZE_TOP_LAYER_ARCTIC = createKey("freeze_top_layer_arctic");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDBED = createKey("cloudbed");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        bootstrapVegetation(context);
        bootstrapTrees(context);
        bootstrapWorldgen(context);
    }

    private static void bootstrapVegetation(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, GRASS_FIELD, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                80,
                12,
                4,
                PlacementUtils.filtered(
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
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE)
                )
        ));
        register(
                context,
                SMALL_GRASS_PATCH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        80,
                        4,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                MEDIUM_GRASS_PATCH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        64,
                        6,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                        .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 3)
                                        .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                LARGE_GRASS_PATCH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        48,
                        8,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                        .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 3)
                                        .add(AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 4)
                                        .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                VALKYRIE_SPROUT_PATCH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        160,
                        4,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.VALKYRIE_SPROUT.get().defaultBlockState().setValue(ValkyrieSproutBlock.AGE, 2))
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                HIGHLANDS_BUSH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        100,
                        2,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.HIGHLANDS_BUSH.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable()))
                )
        );
        register(
                context,
                BLUEBERRY_BUSH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        100,
                        2,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.BLUEBERRY_BUSH.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable()))
                )
        );
        register(context, ORANGE_TREE, AetherIIFeatures.ORANGE_TREE.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(AetherIIBlocks.ORANGE_TREE.get().defaultBlockState().setValue(OrangeTreeBlock.AGE, 4))));

        register(context, AETHER_GRASS_BONEMEAL, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 1)
                .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 1)
                .add(AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 1)
        )));
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

    private static void bootstrapWorldgen(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<DensityFunction> function = context.lookup(Registries.DENSITY_FUNCTION);

        register(context, COAST_QUICKSOIL, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.QUICKSOIL.get()),
                CoastFeature.Type.HIGHFIELDS,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS),
                Optional.empty(),
                UniformInt.of(112, 156),
                0.75F,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));
        register(context, COAST_FERROSITE_SAND, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.FERROSITE_SAND.get()),
                CoastFeature.Type.MAGNETIC,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS),
                Optional.of(DensityFunctions.zero()),
                UniformInt.of(112, 156),
                0.0F,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));
        register(context, COAST_ARCTIC_PACKED_ICE, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.ARCTIC_PACKED_ICE.get()),
                CoastFeature.Type.ARCTIC,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_ARCTIC),
                Optional.empty(),
                UniformInt.of(120, 180),
                0.0F,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));

        register(context, WATER_POND, AetherIIFeatures.LAKE.get(),
                new AetherLakeConfiguration(BlockStateProvider.simple(Blocks.WATER), BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get())));
        register(context, WATER_SPRING, Feature.SPRING,
                new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.UNDERSHALE.get(), AetherIIBlocks.HOLYSTONE.get(), AetherIIBlocks.AETHER_DIRT.get())));

        register(context, NOISE_LAKE, AetherIIFeatures.NOISE_LAKE.get(),
                new NoiseLakeConfiguration(
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_NOISE),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_FLOOR),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_BARRIER),
                        ConstantInt.of(124),
                        new NoiseProvider(
                                100L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.075F,
                                List.of(
                                        AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(),
                                        AetherIIBlocks.HOLYSTONE.get().defaultBlockState()
                                )
                        ),
                        false
                ));
        register(context, NOISE_LAKE_ARCTIC, AetherIIFeatures.NOISE_LAKE.get(),
                new NoiseLakeConfiguration(
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_NOISE),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_FLOOR),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_BARRIER),
                        ConstantInt.of(124),
                        new NoiseProvider(
                                100L,
                                new NormalNoise.NoiseParameters(0, 1.0),
                                0.075F,
                                List.of(
                                        AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(),
                                        AetherIIBlocks.HOLYSTONE.get().defaultBlockState()
                                )
                        ),
                        true
                ));

        register(context, FERROSITE_PILLAR, AetherIIFeatures.FERROSITE_PILLAR.get(), new FerrositePillarConfiguration(
                new NoiseProvider(
                        300L,
                        new NormalNoise.NoiseParameters(0, 1.0),
                        0.064F,
                        List.of(
                                AetherIIBlocks.FERROSITE.get().defaultBlockState(),
                                AetherIIBlocks.FERROSITE.get().defaultBlockState(),
                                AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState()
                        )
                ),
                4.5F,
                6,
                40,
                24,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));
        register(context, FERROSITE_PILLAR_TURF_TOP, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.AETHER_DIRT,
                        BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(AETHER_GRASS_BONEMEAL)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        4,
                        0.0F,
                        UniformInt.of(24, 28),
                        0.3F
                )
        );
        register(context, FERROSITE_PILLAR_TURF, Feature.VEGETATION_PATCH,
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

        register(context, FERROSITE_SPIKE, AetherIIFeatures.FERROSITE_SPIKE.get(), new FerrositeSpikeConfiguration(
                new NoiseProvider(
                        200L,
                        new NormalNoise.NoiseParameters(0, 1.0),
                        0.12F,
                        List.of(
                                AetherIIBlocks.FERROSITE.get().defaultBlockState(),
                                AetherIIBlocks.FERROSITE.get().defaultBlockState(),
                                AetherIIBlocks.RUSTED_FERROSITE.get().defaultBlockState()
                        )
                ),
                2.5F,
                3,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get())
        ));
        register(context, ARCTIC_ICE_SPIKE, AetherIIFeatures.ARCTIC_ICE_SPIKE.get(), new ArcticIceSpikeConfiguration(
                new NoiseProvider(
                        400L,
                        new NormalNoise.NoiseParameters(0, 1.0),
                        0.1F,
                        List.of(
                                AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(),
                                AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState()
                        )
                ),
                4.25F,
                2,
                7.5F,
                5,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get())
        ));
        register(context, MEGA_ARCTIC_ICE_SPIKE, AetherIIFeatures.ARCTIC_ICE_SPIKE.get(), new ArcticIceSpikeConfiguration(
                new NoiseProvider(
                        500L,
                        new NormalNoise.NoiseParameters(0, 1.0),
                        0.1F,
                        List.of(
                                AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState(),
                                AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState()
                        )
                ),
                6.25F,
                3,
                3.5F,
                2,
                HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get())
        ));
        register(context, ARCTIC_ICE_SPIKE_VARIANTS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(MEGA_ARCTIC_ICE_SPIKE)), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ARCTIC_ICE_SPIKE))));

        AetherIIFeatureUtils.register(context, FREEZE_TOP_LAYER_ARCTIC, AetherIIFeatures.FREEZE_TOP_LAYER_ARCTIC.get());

        AetherIIFeatureUtils.register(context, CLOUDBED, AetherIIFeatures.CLOUDBED.get(),
                new CloudbedConfiguration(
                        BlockStateProvider.simple(AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState()),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        96,
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.CLOUDBED_NOISE),
                        10D,
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.CLOUDBED_Y_OFFSET),
                        15D
                ));
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
