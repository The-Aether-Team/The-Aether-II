package com.aetherteam.aetherii.data.resources.registries.highlands;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.*;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDensityFunctions;
import com.aetherteam.aetherii.world.feature.AetherIIFeatures;
import com.aetherteam.aetherii.world.feature.CoastFeature;
import com.aetherteam.aetherii.world.feature.configuration.*;
import com.aetherteam.aetherii.world.tree.decorator.*;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.AmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.LargeAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.amberoot.SingularAmberootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatboaFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatoakFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.greatroot.GreatrootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.skyroot.*;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisprootFoliagePlacer;
import com.aetherteam.aetherii.world.tree.foliage.wisproot.WisptopFoliagePlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
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
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.*;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class HighlandsConfiguredFeatures {
    public static final RuleTest HOLYSTONE_TEST = new TagMatchTest(AetherIITags.Blocks.HOLYSTONE);
    public static final RuleTest UNDERSHALE_TEST = new BlockMatchTest(AetherIIBlocks.UNDERSHALE.get());
    public static final RuleTest UNDERGROUND_TEST = new TagMatchTest(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS);
    
    // Surface
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TWIGS = createKey("skyroot_twigs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLYSTONE_ROCKS = createKey("holystone_rocks");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_HOLYSTONE_BOULDER = createKey("mossy_holystone_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICESTONE_BOULDER = createKey("icestone_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SKYROOT_LOG = createKey("fallen_skyroot_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_WISPROOT_LOG = createKey("fallen_wisproot_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST = createKey("moa_nest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOA_NEST_TREE = createKey("moa_nest_tree");


    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_FIELD = createKey("grass_field");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GRASS_PATCH = createKey("small_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_GRASS_PATCH = createKey("medium_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GRASS_PATCH = createKey("large_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRRADIATED_GRASS_PATCH = createKey("irradiated_grass_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VALKYRIE_SPROUT_PATCH = createKey("valkyrie_sprout_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHLANDS_BUSH = createKey("highlands_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEBERRY_BUSH = createKey("blueberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_TREE = createKey("orange_tree_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRETTL_PLANT = createKey("brettl_plant");

    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHLANDS_FLOWER_PATCH = createKey("highlands_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HIGHFIELDS_FLOWER_PATCH = createKey("highfields_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGNETIC_FLOWER_PATCH = createKey("magnetic_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCTIC_FLOWER_PATCH = createKey("arctic_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGNETIC_SHROOM_PATCH = createKey("magnetic_shroom_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_GRASS_BONEMEAL = createKey("aether_grass_bonemeal");


    // Trees
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBEROOT = createKey("amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_AMBEROOT = createKey("large_amberoot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SINGULAR_AMBEROOT = createKey("singular_amberoot");
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AMBEROOT_SPARSE = createKey("trees_amberoot_sparse");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AMBEROOT_DENSE = createKey("trees_amberoot_dense");

    // Highfields
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT = createKey("skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_WITH_LEAF_PILES = createKey("skyroot_with_leaf_piles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_SKYROOT = createKey("short_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_SKYROOT = createKey("large_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_SKYROOT = createKey("nest_skyroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NEST_SKYROOT_WITH_LEAF_PILES = createKey("nest_skyroot_with_leaf_piles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPLANE = createKey("skyplane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYPLANE_WITH_LEAF_PILES = createKey("skyplane_with_leaf_piles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_SKYPLANE = createKey("short_skyplane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT = createKey("wisproot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPROOT_WITH_LEAF_PILES = createKey("wisproot_with_leaf_piles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATOAK = createKey("greatoak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREATOAK_WITH_LEAF_PILES = createKey("greatoak_with_leaf_piles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_GREATOAK = createKey("short_greatoak");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_FLOURISHING_FIELD = createKey("trees_biome_flourishing_field");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_VERDANT_WOODS = createKey("trees_biome_verdant_woods");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_SHROUDED_FOREST = createKey("trees_biome_shrouded_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIOME_SHIMMERING_BASIN = createKey("trees_biome_shimmering_basin");

    // Magnetic
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYBIRCH = createKey("skybirch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP = createKey("wisptop");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WISPTOP_WITH_LEAF_PILES = createKey("wisptop_with_leaf_piles");
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


    // Underground
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKY_ROOTS = createKey("sky_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_SKY_ROOTS = createKey("frosted_sky_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE = createKey("ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_CRYSTALS = createKey("ice_crystals");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_BLOCKS = createKey("grass_blocks");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENCHANTED_GRASS_BLOCKS = createKey("enchanted_grass_blocks");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_AND_DIRT_FLOOR = createKey("grass_and_dirt_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ENCHANTED_GRASS_AND_DIRT_FLOOR = createKey("enchanted_grass_and_dirt_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_AETHER_DIRT_FLOOR = createKey("coarse_aether_dirt_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_AETHER_DIRT_CEILING = createKey("coarse_aether_dirt_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_AETHER_DIRT_FROSTED_CEILING = createKey("coarse_aether_dirt_frosted_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_CEILING = createKey("ice_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRYALINN_MOSS_CARPET = createKey("bryalinn_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRYALINN_MOSS_FLOWERS = createKey("bryalinn_moss_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRYALINN_MOSS_VINES = createKey("bryalinn_moss_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BRYALINN_MOSS_FLOOR = createKey("bryalinn_moss_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHAYELINN_MOSS_CARPET = createKey("shayelinn_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHAYELINN_MOSS_VINES = createKey("shayelinn_moss_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHAYELINN_MOSS_FLOOR = createKey("shayelinn_moss_floor");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SCATTERGLASS = createKey("ore_scatterglass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE = createKey("ore_icestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ICESTONE_SMALL = createKey("ore_icestone_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AGIOSITE = createKey("ore_agiosite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HOLYSTONE_QUARTZ = createKey("ore_holystone_quartz");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AMBROSIUM = createKey("ore_ambrosium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ZANITE = createKey("ore_zanite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GLINT = createKey("ore_glint");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ARKENIUM = createKey("ore_arkenium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE_BURIED = createKey("ore_gravitite_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GRAVITITE = createKey("ore_gravitite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CORROBONITE = createKey("ore_corrobonite");
    
    
    // Worldgen
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_QUICKSOIL = createKey("coast_quicksoil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_FERROSITE_SAND = createKey("coast_ferrosite_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAST_ARCTIC_PACKED_ICE = createKey("coast_arctic_packed_ice");

    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_POND = createKey("water_pond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_POND_TUNDRA = createKey("water_pond_tundra");
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> FREEZE_TOP_LAYER_TUNDRA = createKey("freeze_top_layer_tundra");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDBED = createKey("cloudbed");
    
    
    // Air
    public static final ResourceKey<ConfiguredFeature<?, ?>> COLD_AERCLOUD = createKey("cold_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_AERCLOUD = createKey("golden_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_AERCLOUD = createKey("blue_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_AERCLOUD = createKey("green_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_AERCLOUD = createKey("purple_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STORM_AERCLOUD = createKey("storm_aercloud");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        bootstrapSurface(context);
        bootstrapVegetation(context);
        bootstrapTrees(context);
        bootstrapUnderground(context);
        bootstrapWorldgen(context);
        bootstrapAir(context);
    }

    private static void bootstrapSurface(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        SimpleWeightedRandomList.Builder<BlockState> twigs = new SimpleWeightedRandomList.Builder<>();
        for (Direction facing : TwigBlock.FACING.getPossibleValues()) {
            for (int amount : TwigBlock.AMOUNT.getPossibleValues()) {
                twigs.add(AetherIIBlocks.SKYROOT_TWIG.get().defaultBlockState().setValue(TwigBlock.FACING, facing).setValue(TwigBlock.AMOUNT, amount), amount);
            }
        }

        SimpleWeightedRandomList.Builder<BlockState> rocks = new SimpleWeightedRandomList.Builder<>();
        for (Direction facing : RockBlock.FACING.getPossibleValues()) {
            for (int amount : RockBlock.AMOUNT.getPossibleValues()) {
                rocks.add(AetherIIBlocks.HOLYSTONE_ROCK.get().defaultBlockState().setValue(RockBlock.FACING, facing).setValue(RockBlock.AMOUNT, amount), amount);
            }
        }

        register(
                context,
                SKYROOT_TWIGS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        2,
                        1,
                        1,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(twigs)),
                                BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SKYROOT_TWIG_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                HOLYSTONE_ROCKS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        4,
                        2,
                        2,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(rocks)),
                                BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.HOLYSTONE_ROCK_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(context, MOSSY_HOLYSTONE_BOULDER, AetherIIFeatures.BOULDER.get(), new BoulderConfiguration(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(AetherIIBlocks.MOSSY_HOLYSTONE.get().defaultBlockState(), 4)
                        .add(AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 1)
                        .build()),
                0.5F,
                UniformFloat.of(0.0F, 1.0F),
                Optional.of(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(HOLYSTONE_ROCKS), CountPlacement.of(UniformInt.of(1, 6)))),
                1.0F));
        register(context, ICESTONE_BOULDER, AetherIIFeatures.BOULDER.get(), new BoulderConfiguration(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(AetherIIBlocks.ICESTONE.get().defaultBlockState(), 1)
                        .add(AetherIIBlocks.HOLYSTONE.get().defaultBlockState(), 3)
                        .build()),
                0.5F,
                UniformFloat.of(0.0F, 1.0F),
                Optional.empty(),
                0.0F));
        register(context, FALLEN_SKYROOT_LOG, AetherIIFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get()),
                UniformInt.of(2, 4),
                Optional.empty(),
                0.0F,
                AetherIITags.Blocks.FALLEN_LOG_SURVIVES_ON
        ));
        register(context, FALLEN_WISPROOT_LOG, AetherIIFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get()),
                UniformInt.of(3, 6),
                Optional.empty(),
                0.0F,
                AetherIITags.Blocks.FALLEN_LOG_SURVIVES_ON
        ));
        register(context, MOA_NEST, AetherIIFeatures.MOA_NEST.get(), new MoaNestConfiguration(BlockStateProvider.simple(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()), 1.5F, 2, true));
        register(context, MOA_NEST_TREE, AetherIIFeatures.MOA_NEST.get(), new MoaNestConfiguration(BlockStateProvider.simple(AetherIIBlocks.WOVEN_SKYROOT_STICKS.get()), 1.5F, 2, true));
    }

    private static void bootstrapVegetation(BootstrapContext<ConfiguredFeature<?, ?>> context) {
//        register(context, GRASS_FIELD, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
//                80,
//                12,
//                4,
//                PlacementUtils.filtered(
//                        AetherIIFeatures.AETHER_GRASS.get(),
//                        new SimpleBlockConfiguration(
//                                new NoiseProvider(
//                                        2345L,
//                                        new NormalNoise.NoiseParameters(0, 1.0),
//                                        0.02F,
//                                        List.of(
//                                                AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(),
//                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(),
//                                                AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(),
//                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(),
//                                                AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState()
//                                        )
//                                )
//                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE)
//                )
//        ));
        register(context, GRASS_FIELD, Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                80,
                12,
                4,
                PlacementUtils.filtered(
                        AetherIIFeatures.AETHER_GRASS.get(),
                        new SimpleBlockConfiguration(
                                new DualNoiseProvider(
                                        new InclusiveRange<>(1, 3),
                                        new NormalNoise.NoiseParameters(5, 1.0),
                                        0.02F,
                                        2345L,
                                        new NormalNoise.NoiseParameters(0, 1.0),
                                        0.02F,
                                        List.of(
                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(),
                                                AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState()
                                        )
                                )
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE)
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
                        PlacementUtils.filtered(AetherIIFeatures.AETHER_GRASS.get(), new SimpleBlockConfiguration(
                                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                        .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.HIGHLAND_FERN.get().defaultBlockState(), 1)
                                        .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
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
                        PlacementUtils.filtered(AetherIIFeatures.AETHER_GRASS.get(), new SimpleBlockConfiguration(
                                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                        .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 3)
                                        .add(AetherIIBlocks.HIGHLAND_FERN.get().defaultBlockState(), 1)
                                        .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
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
                        PlacementUtils.filtered(AetherIIFeatures.AETHER_GRASS.get(), new SimpleBlockConfiguration(
                                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                        .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 3)
                                        .add(AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 4)
                                        .add(AetherIIBlocks.HIGHLAND_FERN.get().defaultBlockState(), 1)
                                        .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                IRRADIATED_GRASS_PATCH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        64,
                        8,
                        4,
                        PlacementUtils.filtered(AetherIIFeatures.AETHER_GRASS.get(), new SimpleBlockConfiguration(
                                new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                        .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 1)
                                        .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 1)
                                        .add(AetherIIBlocks.HIGHLAND_FERN.get().defaultBlockState(), 1)
                                        .add(AetherIIBlocks.SHIELD_FERN.get().defaultBlockState(), 2)
                                        .add(AetherIIBlocks.BLADE_POA.get().defaultBlockState(), 2)
                                        .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
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
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
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
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable()))
                )
        );
        register(
                context,
                BLUEBERRY_BUSH,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        55,
                        2,
                        3,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.BLUEBERRY_BUSH.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable()))
                )
        );
        register(context, ORANGE_TREE, AetherIIFeatures.ORANGE_TREE.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(AetherIIBlocks.ORANGE_TREE.get().defaultBlockState().setValue(OrangeTreeBlock.AGE, 4))));
        register(context, BRETTL_PLANT, AetherIIFeatures.BRETTL_PLANT.get(), new NoneFeatureConfiguration());

        register(
                context,
                HIGHLANDS_FLOWER_PATCH,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        16,
                        8,
                        3,
                        PlacementUtils.filtered(AetherIIFeatures.AETHER_FLOWER.get(), new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(AetherIIBlocks.HESPEROSE.get().defaultBlockState(), 1)
                                .add(AetherIIBlocks.TARABLOOM.get().defaultBlockState(), 1)
                                .add(AetherIIBlocks.POASPROUT.get().defaultBlockState(), 1)
                                .add(AetherIIBlocks.LILICHIME.get().defaultBlockState(), 1)
                                .add(AetherIIBlocks.PLURACIAN.get().defaultBlockState(), 1)
                                .add(AetherIIBlocks.SATIVAL_SHOOT.get().defaultBlockState(), 1)

                        )), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable()))
        ));
        register(
                context,
                HIGHFIELDS_FLOWER_PATCH,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        40,
                        8,
                        3,
                        PlacementUtils.filtered(
                                AetherIIFeatures.AETHER_FLOWER.get(),
                                new SimpleBlockConfiguration(
                                        new DualNoiseProvider(
                                                new InclusiveRange<>(1, 3),
                                                new NormalNoise.NoiseParameters(-5, 1.0),
                                                1.0F,
                                                2345L,
                                                new NormalNoise.NoiseParameters(-1, 1.0),
                                                1.0F,
                                                List.of(
//                                                        AetherIIBlocks.RED_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.ORANGE_CLOUDWOOL.get().defaultBlockState(),
                                                        AetherIIBlocks.HESPEROSE.get().defaultBlockState(),
                                                        AetherIIBlocks.TARABLOOM.get().defaultBlockState()
//                                                        AetherIIBlocks.GREEN_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.CYAN_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.BLUE_CLOUDWOOL.get().defaultBlockState()

                                                )
                                        )
                                ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable())
                        )
                )
        );
        register(
                context,
                MAGNETIC_FLOWER_PATCH,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        40,
                        8,
                        3,
                        PlacementUtils.filtered(
                                AetherIIFeatures.AETHER_FLOWER.get(),
                                new SimpleBlockConfiguration(
                                        new DualNoiseProvider(
                                                new InclusiveRange<>(1, 3),
                                                new NormalNoise.NoiseParameters(-5, 1.0),
                                                1.0F,
                                                2345L,
                                                new NormalNoise.NoiseParameters(-1, 1.0),
                                                1.0F,
                                                List.of(
//                                                        AetherIIBlocks.RED_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.ORANGE_CLOUDWOOL.get().defaultBlockState(),
                                                        AetherIIBlocks.POASPROUT.get().defaultBlockState(),
                                                        AetherIIBlocks.LILICHIME.get().defaultBlockState(),
                                                        AetherIIBlocks.PLURACIAN.get().defaultBlockState()
//                                                        AetherIIBlocks.GREEN_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.CYAN_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.BLUE_CLOUDWOOL.get().defaultBlockState()

                                                )
                                        )
                                ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable())
                        )
                )
        );

        register(
                context,
                ARCTIC_FLOWER_PATCH,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        40,
                        8,
                        3,
                        PlacementUtils.filtered(
                                AetherIIFeatures.AETHER_FLOWER.get(),
                                new SimpleBlockConfiguration(
                                        new DualNoiseProvider(
                                                new InclusiveRange<>(1, 3),
                                                new NormalNoise.NoiseParameters(-5, 1.0),
                                                1.0F,
                                                2345L,
                                                new NormalNoise.NoiseParameters(-1, 1.0),
                                                1.0F,
                                                List.of(
//                                                        AetherIIBlocks.RED_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.ORANGE_CLOUDWOOL.get().defaultBlockState(),
                                                        AetherIIBlocks.SATIVAL_SHOOT.get().defaultBlockState()
//                                                        AetherIIBlocks.GREEN_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.CYAN_CLOUDWOOL.get().defaultBlockState(),
//                                                        AetherIIBlocks.BLUE_CLOUDWOOL.get().defaultBlockState()

                                                )
                                        )
                                ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable())
                        )
                )
        );
        register(context, MAGNETIC_SHROOM_PATCH, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(SimpleStateProvider.simple(AetherIIBlocks.MAGNETIC_SHROOM.get()))));

        register(context, AETHER_GRASS_BONEMEAL, AetherIIFeatures.AETHER_GRASS.get(), new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(AetherIIBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 1)
                .add(AetherIIBlocks.AETHER_MEDIUM_GRASS.get().defaultBlockState(), 1)
                .add(AetherIIBlocks.AETHER_LONG_GRASS.get().defaultBlockState(), 1)
        )));
    }

    private static void bootstrapTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        SimpleWeightedRandomList.Builder<BlockState> twigs = new SimpleWeightedRandomList.Builder<>();
        for (Direction facing : TwigBlock.FACING.getPossibleValues()) {
            for (int amount : TwigBlock.AMOUNT.getPossibleValues()) {
                twigs.add(AetherIIBlocks.SKYROOT_TWIG.get().defaultBlockState().setValue(TwigBlock.FACING, facing).setValue(TwigBlock.AMOUNT, amount), amount);
            }
        }
        
        register(context, AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(5, 4, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new AmberootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines().build());
        register(context, LARGE_AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(8, 5, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new LargeAmberootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(3)))
                        .ignoreVines().build());
        register(context, SINGULAR_AMBEROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherIIBlocks.AMBEROOT_LOG.get().defaultBlockState(), 3).add(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState(), 2).build()),
                        new StraightTrunkPlacer(6, 4, 0), BlockStateProvider.simple(AetherIIBlocks.AMBEROOT_LEAVES.get().defaultBlockState()),
                        new SingularAmberootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1))
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
        register(context, SKYROOT_WITH_LEAF_PILES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(4, 2, 1), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1))
                        .ignoreVines()
                        .decorators(List.of(new GroundFeatureDecorator(BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAF_PILE.get().defaultBlockState().setValue(AetherLeafPileBlock.PERSISTENT, true)), 3))).build());
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
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(3)))
                        .ignoreVines().build());
        register(context, NEST_SKYROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(12, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new NestSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                        .ignoreVines().build());
        register(context, NEST_SKYROOT_WITH_LEAF_PILES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(12, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAVES.get().defaultBlockState()),
                        new NestSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                        .ignoreVines()
                        .decorators(List.of(new GroundFeatureDecorator(BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LEAF_PILE.get().defaultBlockState().setValue(AetherLeafPileBlock.PERSISTENT, true)), 3))).build());
        register(context, SKYPLANE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 2), BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 0, OptionalInt.empty()))
                        .ignoreVines().build());
        register(context, SKYPLANE_WITH_LEAF_PILES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 2), BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 0, OptionalInt.empty()))
                        .ignoreVines()
                        .decorators(List.of(new GroundFeatureDecorator(BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAF_PILE.get().defaultBlockState().setValue(AetherLeafPileBlock.PERSISTENT, true)), 3))).build());
        register(context, SHORT_SKYPLANE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 3, 0), BlockStateProvider.simple(AetherIIBlocks.SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 0, OptionalInt.empty()))
                        .ignoreVines().build());
        register(context, WISPROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(2, 0, 1))
                        .ignoreVines().decorators(List.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());
        register(context, WISPROOT_WITH_LEAF_PILES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(2, 0, 1))
                        .ignoreVines().decorators(List.of(
                                new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()),
                                new GroundFeatureDecorator(BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LEAF_PILE.get().defaultBlockState().setValue(AetherLeafPileBlock.PERSISTENT, true)), 3))).build());
        register(context, GREATOAK, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(16, 2, 1), BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 1, 2))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).build());
        register(context, GREATOAK_WITH_LEAF_PILES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(20, 15, 4), BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 1, 2))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState()))
                        .decorators(List.of(new GroundFeatureDecorator(BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAF_PILE.get().defaultBlockState().setValue(AetherLeafPileBlock.PERSISTENT, true)), 3))).build());
        register(context, SHORT_GREATOAK, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(12, 2, 0), BlockStateProvider.simple(AetherIIBlocks.GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2))
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
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHORT_GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_DENSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.05F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()))));
        register(context, TREES_BIOME_SHROUDED_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPLANE_WITH_LEAF_PILES), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.1F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.025F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYROOT_WITH_LEAF_PILES), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(NEST_SKYROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.0015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(NEST_SKYROOT_WITH_LEAF_PILES), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get())), 0.0005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPROOT_WITH_LEAF_PILES), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPROOT_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATOAK), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.015F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATOAK_WITH_LEAF_PILES), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATOAK_SAPLING.get())), 0.005F),
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
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 0, OptionalInt.empty()))
                        .ignoreVines().build());
        register(context, WISPTOP, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(2, 0, 1))
                        .ignoreVines().decorators(List.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()))).build());
        register(context, WISPTOP_WITH_LEAF_PILES, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(2, 0, 1))
                        .ignoreVines().decorators(List.of(
                                new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()),
                                new GroundFeatureDecorator(BlockStateProvider.simple(AetherIIBlocks.WISPTOP_LEAF_PILE.get().defaultBlockState().setValue(AetherLeafPileBlock.PERSISTENT, true)), 3))).build());
        register(context, GREATROOT, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(6, 2, 5), BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LEAVES.get().defaultBlockState()),
                        new GreatrootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2))
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
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPTOP_WITH_LEAF_PILES), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get())), 0.25F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYBIRCH), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYBIRCH_SAPLING.get())), 0.005F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATROOT), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.GREATROOT_SAPLING.get())), 0.002F),
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TREES_AMBEROOT_SPARSE), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.AMBEROOT_SAPLING.get())), 0.001F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(WISPTOP), PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.WISPTOP_SAPLING.get()))));

        // Arctic
        register(context, SKYPINE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(6, 4, 1), BlockStateProvider.simple(AetherIIBlocks.SKYPINE_LEAVES.get().defaultBlockState()),
                        new SkypineFoliagePlacer(UniformInt.of(3, 5), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 0, 2))
                        .ignoreVines()
                        .decorators(List.of(
                                new AlterGroundTagDecorator(BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get()), AetherIITags.Blocks.GRASS_SNOW_REPLACEABLE),
                                new SnowDecorator(),
                                new GroundFeatureDecorator(new WeightedStateProvider(twigs), 50)))
                        .build());
        register(context, GREATBOA, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(15, 3, 8), BlockStateProvider.simple(AetherIIBlocks.GREATBOA_LEAVES.get().defaultBlockState()),
                        new GreatboaFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 2, 2))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState()))
                        .decorators(List.of(
                                new AlterGroundTagDecorator(BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get()), AetherIITags.Blocks.GRASS_SNOW_REPLACEABLE),
                                new SnowDecorator(),
                                new GroundFeatureDecorator(new WeightedStateProvider(twigs), 50)))
                        .build());

        register(context, TREES_BIOME_FRIGID_SIERRA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKYPINE), BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.ARCTIC_TREE_SURVIVES_ON))), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GREATBOA), BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.ARCTIC_TREE_SURVIVES_ON)))));
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
                        .ignoreVines().decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, LARGE_SKYROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(7, 6, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYROOT_LEAVES.get().defaultBlockState()),
                        new LargeSkyrootFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(3)))
                        .ignoreVines().decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, SKYPLANE_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(10, 4, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYPLANE_LEAVES.get().defaultBlockState()),
                        new SkyplaneFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 0, OptionalInt.empty()))
                        .ignoreVines().decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, SKYBIRCH_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(8, 3, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYBIRCH_LEAVES.get().defaultBlockState()),
                        new SkybirchFoliagePlacer(ConstantInt.of(1), ConstantInt.of(2)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 0, OptionalInt.empty()))
                        .ignoreVines().decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, SKYPINE_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.SKYROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(6, 4, 1), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_SKYPINE_LEAVES.get().defaultBlockState()),
                        new SkypineFoliagePlacer(UniformInt.of(3, 5), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 0, 2))
                        .ignoreVines().decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, WISPROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(11, 4, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_WISPROOT_LEAVES.get().defaultBlockState()),
                        new WisprootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(2, 0, 1))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()), new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, WISPTOP_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.WISPROOT_LOG.get().defaultBlockState()),
                        new StraightTrunkPlacer(13, 6, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_WISPTOP_LEAVES.get().defaultBlockState()),
                        new WisptopFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(2, 0, 1))
                        .ignoreVines().decorators(ImmutableList.of(new WisprootTreeDecorator(AetherIIBlocks.MOSSY_WISPROOT_LOG.get().defaultBlockState()), new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, GREATROOT_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(6, 2, 5), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATROOT_LEAVES.get().defaultBlockState()),
                        new GreatrootFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, GREATOAK_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(12, 2, 0), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATOAK_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());
        register(context, GREATBOA_IRRADIATED, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherIIBlocks.GREATROOT_LOG.get().defaultBlockState()),
                        new GiantTrunkPlacer(15, 3, 8), BlockStateProvider.simple(AetherIIBlocks.IRRADIATED_GREATBOA_LEAVES.get().defaultBlockState()),
                        new GreatoakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 2, 2))
                        .ignoreVines().dirt(BlockStateProvider.simple(AetherIIBlocks.AETHER_DIRT.get().defaultBlockState())).decorators(ImmutableList.of(new IrradiationTreeDecorator(), new MossDecorator(SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_BLOCK.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_CARPET.get()), SimpleStateProvider.simple(AetherIIBlocks.TARAHESP_AMBRELINN_MOSS_VINES.get())))).build());

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

    private static void bootstrapUnderground(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        List<OreConfiguration.TargetBlockState> quartz = List.of(
                OreConfiguration.target(HOLYSTONE_TEST, AetherIIBlocks.HOLYSTONE_QUARTZ_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> ambrosium = List.of(
                OreConfiguration.target(HOLYSTONE_TEST, AetherIIBlocks.AMBROSIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE_TEST, AetherIIBlocks.UNDERSHALE_AMBROSIUM_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> zanite = List.of(
                OreConfiguration.target(HOLYSTONE_TEST, AetherIIBlocks.ZANITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE_TEST, AetherIIBlocks.UNDERSHALE_ZANITE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> glint = List.of(
                OreConfiguration.target(HOLYSTONE_TEST, AetherIIBlocks.GLINT_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE_TEST, AetherIIBlocks.UNDERSHALE_GLINT_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> arkenium = List.of(
                OreConfiguration.target(HOLYSTONE_TEST, AetherIIBlocks.ARKENIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE_TEST, AetherIIBlocks.UNDERSHALE_ARKENIUM_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> gravitite = List.of(
                OreConfiguration.target(HOLYSTONE_TEST, AetherIIBlocks.GRAVITITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(UNDERSHALE_TEST, AetherIIBlocks.UNDERSHALE_GRAVITITE_ORE.get().defaultBlockState()));

        SimpleWeightedRandomList.Builder<BlockState> bryalinnFlowers = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                bryalinnFlowers.add(AetherIIBlocks.BRYALINN_MOSS_FLOWERS.get().defaultBlockState().setValue(BryalinnFlowersBlock.AMOUNT, i).setValue(BryalinnFlowersBlock.FACING, direction), 1);
            }
        }

        register(
                context,
                SKY_ROOTS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20,
                        4,
                        4,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.SKY_ROOTS.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.above(), AetherIITags.Blocks.SKY_ROOTS_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                FROSTED_SKY_ROOTS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20,
                        4,
                        4,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.SKY_ROOTS.get().defaultBlockState().setValue(AetherHangingRootsBlock.SNOWY, true))
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.above(), AetherIITags.Blocks.SKY_ROOTS_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(context,
                ICE,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20,
                        4,
                        4,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.ARCTIC_PACKED_ICE.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesBlocks(Vec3i.ZERO.above(), AetherIIBlocks.ARCTIC_PACKED_ICE.get()), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(
                context,
                ICE_CRYSTALS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20,
                        4,
                        4,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(AetherIIBlocks.SMALL_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 1)
                                .add(AetherIIBlocks.MEDIUM_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 1)
                                .add(AetherIIBlocks.LARGE_ARCTIC_ICE_CRYSTAL.get().defaultBlockState().setValue(IceCrystalBlock.FACING, Direction.DOWN), 1)
                                .build())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.above(), AetherIITags.Blocks.ICE_CRYSTAL_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE))
                )
        );
        register(context,
                GRASS_BLOCKS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20,
                        4,
                        4,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(AetherIITags.Blocks.GRASS_AND_DIRT_REPLACEABLE), BlockPredicate.matchesBlocks(Vec3i.ZERO.above(), Blocks.AIR)))
                )
        );
        register(context,
                ENCHANTED_GRASS_BLOCKS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        20,
                        4,
                        4,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(AetherIIBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState())
                        ), BlockPredicate.allOf(BlockPredicate.matchesTag(AetherIITags.Blocks.GRASS_AND_DIRT_REPLACEABLE), BlockPredicate.matchesBlocks(Vec3i.ZERO.above(), Blocks.AIR)))
                )
        );
        register(
                context,
                GRASS_AND_DIRT_FLOOR,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.GRASS_AND_DIRT_REPLACEABLE,
                        BlockStateProvider.simple(AetherIIBlocks.COARSE_AETHER_DIRT.get()),
                        PlacementUtils.inlinePlaced(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                                List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(GRASS_BLOCKS)), 0.25F)),
                                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(MEDIUM_GRASS_PATCH)))),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        3,
                        0.65F,
                        UniformInt.of(2, 5),
                        0.75F
                )
        );
        register(
                context,
                ENCHANTED_GRASS_AND_DIRT_FLOOR,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.GRASS_AND_DIRT_REPLACEABLE,
                        BlockStateProvider.simple(AetherIIBlocks.COARSE_AETHER_DIRT.get()),
                        PlacementUtils.inlinePlaced(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                                List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ENCHANTED_GRASS_BLOCKS)), 0.25F)),
                                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(IRRADIATED_GRASS_PATCH)))),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        3,
                        0.9F,
                        UniformInt.of(2, 5),
                        0.75F
                )
        );
        register(
                context,
                COARSE_AETHER_DIRT_FLOOR,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.COARSE_AETHER_DIRT_REPLACEABLE,
                        BlockStateProvider.simple(AetherIIBlocks.COARSE_AETHER_DIRT.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(HOLYSTONE_ROCKS)),
                        CaveSurface.FLOOR,
                        UniformInt.of(1, 2),
                        0.1F,
                        3,
                        0.035F,
                        UniformInt.of(1, 4),
                        0.75F
                )
        );
        register(
                context,
                COARSE_AETHER_DIRT_CEILING,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.COARSE_AETHER_DIRT_REPLACEABLE,
                        BlockStateProvider.simple(AetherIIBlocks.COARSE_AETHER_DIRT.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SKY_ROOTS)),
                        CaveSurface.CEILING,
                        UniformInt.of(1, 2),
                        0.1F,
                        3,
                        0.125F,
                        UniformInt.of(1, 4),
                        0.75F
                )
        );
        register(
                context,
                COARSE_AETHER_DIRT_FROSTED_CEILING,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.COARSE_AETHER_DIRT_REPLACEABLE,
                        BlockStateProvider.simple(AetherIIBlocks.COARSE_AETHER_DIRT.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(FROSTED_SKY_ROOTS)),
                        CaveSurface.CEILING,
                        UniformInt.of(1, 2),
                        0.1F,
                        3,
                        0.125F,
                        UniformInt.of(1, 4),
                        0.75F
                )
        );
        register(
                context,
                ICE_CEILING,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.ARCTIC_ICE_REPLACEABLE,
                        BlockStateProvider.simple(AetherIIBlocks.ARCTIC_PACKED_ICE.get()),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ICE_CRYSTALS)),
                        CaveSurface.CEILING,
                        UniformInt.of(1, 2),
                        0.35F,
                        3,
                        0.35F,
                        UniformInt.of(1, 3),
                        0.75F
                )
        );
        register(context,
                BRYALINN_MOSS_CARPET,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        3,
                        2,
                        2,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AetherIIBlocks.BRYALINN_MOSS_CARPET.get())), BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.solid(BlockPos.ZERO.below())))
                )
        );
        FeatureUtils.register(context,
                BRYALINN_MOSS_FLOWERS,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        3,
                        2,
                        2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(bryalinnFlowers)))
                )
        );
        register(context,
                BRYALINN_MOSS_VINES,
                AetherIIFeatures.MOSS_VINES.get(),
                new MossVinesConfiguration(SimpleStateProvider.simple(AetherIIBlocks.BRYALINN_MOSS_VINES.get()))
        );
        register(
                context,
                BRYALINN_MOSS_FLOOR,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS,
                        BlockStateProvider.simple(AetherIIBlocks.BRYALINN_MOSS_BLOCK.get()),
                        PlacementUtils.inlinePlaced(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                                List.of(
                                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BRYALINN_MOSS_CARPET)), 0.2F),
                                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BRYALINN_MOSS_FLOWERS)), 0.3F),
                                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(MEDIUM_GRASS_PATCH)), 0.1F)
                                ),
                                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(BRYALINN_MOSS_VINES), CountPlacement.of(16), RandomOffsetPlacement.of(UniformInt.of(-1, 1), UniformInt.of(-1, 1))))),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.35F,
                        5,
                        0.925F,
                        UniformInt.of(1, 4),
                        0.5F
                )
        );
        register(context,
                SHAYELINN_MOSS_CARPET,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        3,
                        2,
                        2,
                        PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(AetherIIBlocks.SHAYELINN_MOSS_CARPET.get())), BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.solid(BlockPos.ZERO.below())))
                )
        );
        register(context,
                SHAYELINN_MOSS_VINES,
                AetherIIFeatures.MOSS_VINES.get(),
                new MossVinesConfiguration(SimpleStateProvider.simple(AetherIIBlocks.SHAYELINN_MOSS_VINES.get()))
        );
        register(
                context,
                SHAYELINN_MOSS_FLOOR,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        AetherIITags.Blocks.AETHER_DIRT,
                        BlockStateProvider.simple(AetherIIBlocks.SHAYELINN_MOSS_BLOCK.get()),
                        PlacementUtils.inlinePlaced(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                                List.of(
                                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHAYELINN_MOSS_CARPET)), 0.4F),
                                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(MEDIUM_GRASS_PATCH)), 0.2F)
                                ),
                                PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(SHAYELINN_MOSS_VINES), CountPlacement.of(16), RandomOffsetPlacement.of(UniformInt.of(-1, 1), UniformInt.of(-1, 1))))),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.35F,
                        5,
                        0.925F,
                        UniformInt.of(1, 4),
                        0.5F
                )
        );

        register(context, ORE_SCATTERGLASS, Feature.ORE, new OreConfiguration(UNDERGROUND_TEST, AetherIIBlocks.CRUDE_SCATTERGLASS.get().defaultBlockState(), 24));
        register(context, ORE_ICESTONE, Feature.ORE, new OreConfiguration(HOLYSTONE_TEST, AetherIIBlocks.ICESTONE.get().defaultBlockState(), 32));
        register(context, ORE_ICESTONE_SMALL, Feature.ORE, new OreConfiguration(HOLYSTONE_TEST, AetherIIBlocks.ICESTONE.get().defaultBlockState(), 16));
        register(context, ORE_AGIOSITE, Feature.ORE, new OreConfiguration(UNDERSHALE_TEST, AetherIIBlocks.AGIOSITE.get().defaultBlockState(), 38));

        register(context, ORE_HOLYSTONE_QUARTZ, Feature.ORE, new OreConfiguration(quartz, 32));
        register(context, ORE_AMBROSIUM, Feature.ORE, new OreConfiguration(ambrosium, 16));
        register(context, ORE_ZANITE, Feature.ORE, new OreConfiguration(zanite, 5, 0.5F));
        register(context, ORE_GLINT, Feature.ORE, new OreConfiguration(glint, 4));
        register(context, ORE_ARKENIUM, Feature.ORE, new OreConfiguration(arkenium, 5, 0.5F));
        register(context, ORE_GRAVITITE_BURIED, Feature.ORE, new OreConfiguration(gravitite, 3, 0.5F));
        register(context, ORE_GRAVITITE, Feature.ORE, new OreConfiguration(gravitite, 4));
        register(context, ORE_CORROBONITE, AetherIIFeatures.CORROBONITE_ORE.get(), new OreConfiguration(UNDERSHALE_TEST, AetherIIBlocks.CORROBONITE_ORE.get().defaultBlockState(), 4));
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
                Optional.of(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.BRETTL_PLANT),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                        BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.BRETTL_PLANT_SURVIVES_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE)))),
                0.01F,
                AetherIITags.Blocks.QUICKSOIL_COAST_GENERATES_ON
        ));
        register(context, COAST_FERROSITE_SAND, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.FERROSITE_SAND.get()),
                CoastFeature.Type.MAGNETIC,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_HIGHFIELDS),
                Optional.of(DensityFunctions.zero()),
                UniformInt.of(112, 156),
                Optional.empty(),
                0.0F,
                AetherIITags.Blocks.FERROSITE_COAST_GENERATES_ON
        ));
        register(context, COAST_ARCTIC_PACKED_ICE, AetherIIFeatures.COAST.get(), new CoastConfiguration(
                BlockStateProvider.simple(AetherIIBlocks.ARCTIC_PACKED_ICE.get()),
                CoastFeature.Type.ARCTIC,
                16.35F,
                AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.COASTS_ARCTIC),
                Optional.empty(),
                UniformInt.of(120, 180),
                Optional.of(PlacementUtils.inlinePlaced(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                        List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ICE_CRYSTALS)), 0.35F)),
                        PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ICE))))),
                0.25F,
                AetherIITags.Blocks.ARCTIC_COAST_GENERATES_ON
        ));

        register(context, WATER_POND, AetherIIFeatures.LAKE.get(),
                new AetherLakeConfiguration(ConstantInt.of(2), BlockStateProvider.simple(Blocks.WATER), new NoiseProvider(
                        2345L,
                        new NormalNoise.NoiseParameters(0, 1.0),
                        0.25F,
                        List.of(
                                AetherIIBlocks.SHIMMERING_SILT.get().defaultBlockState(),
                                AetherIIBlocks.SHIMMERING_SILT.get().defaultBlockState(),
                                AetherIIBlocks.COARSE_AETHER_DIRT.get().defaultBlockState(),
                                AetherIIBlocks.SHIMMERING_SILT.get().defaultBlockState()
                        )
                )));

        register(context, WATER_POND_TUNDRA, AetherIIFeatures.LAKE.get(),
                new AetherLakeConfiguration(UniformInt.of(2, 5), BlockStateProvider.simple(Blocks.WATER), SimpleStateProvider.simple(AetherIIBlocks.COARSE_AETHER_DIRT.get())));
        register(context, WATER_SPRING, Feature.SPRING,
                new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherIIBlocks.UNDERSHALE.get(), AetherIIBlocks.HOLYSTONE.get(), AetherIIBlocks.AETHER_DIRT.get())));

        register(context, NOISE_LAKE, AetherIIFeatures.NOISE_LAKE.get(),
                new NoiseLakeConfiguration(
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_NOISE),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_FLOOR),
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.LAKES_BARRIER),
                        ConstantInt.of(124),
                        new DualNoiseProvider(
                                new InclusiveRange<>(1, 4),
                                new NormalNoise.NoiseParameters(-6, 1.0),
                                1.0F,
                                2345L,
                                new NormalNoise.NoiseParameters(-2, 1.0),
                                1.0F,
                                List.of(
                                        AetherIIBlocks.SHIMMERING_SILT.get().defaultBlockState(),
                                        AetherIIBlocks.COARSE_AETHER_DIRT.get().defaultBlockState(),
                                        AetherIIBlocks.SHIMMERING_SILT.get().defaultBlockState(),
                                        AetherIIBlocks.AETHER_DIRT.get().defaultBlockState(),
                                        AetherIIBlocks.SHIMMERING_SILT.get().defaultBlockState()
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
                AetherIITags.Blocks.FERROSITE_PILLAR_GENERATES_ON
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
                AetherIITags.Blocks.FERROSITE_SPIKE_GENERATES_ON
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
                AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON
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
                AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON
        ));
        register(context, ARCTIC_ICE_SPIKE_VARIANTS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
                new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(MEGA_ARCTIC_ICE_SPIKE)), 0.1F)
        ), PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(ARCTIC_ICE_SPIKE))));

        register(context, FREEZE_TOP_LAYER_ARCTIC, AetherIIFeatures.FREEZE_TOP_LAYER_ARCTIC.get());
        register(context, FREEZE_TOP_LAYER_TUNDRA, AetherIIFeatures.FREEZE_TOP_LAYER_TUNDRA.get());

        register(context, CLOUDBED, AetherIIFeatures.CLOUDBED.get(),
                new CloudbedConfiguration(
                        new NoiseProvider(
                                2345L,
                                new NormalNoise.NoiseParameters(-7, 1.25, -0.25, 1.0, 0.5, 1.25),
                                1.0F,
                                List.of(
                                        AetherIIBlocks.GREEN_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState(),
                                        AetherIIBlocks.BLUE_AERCLOUD.get().defaultBlockState()
                                )
                        ),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        96,
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.CLOUDBED_NOISE),
                        10D,
                        AetherIIDensityFunctions.getFunction(function, AetherIIDensityFunctions.CLOUDBED_Y_OFFSET),
                        15D
                ));
    }
    
    private static void bootstrapAir(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        SimpleWeightedRandomList.Builder<BlockState> purpleAerclouds = new SimpleWeightedRandomList.Builder<>();
        for (Direction direction : PurpleAercloudBlock.DIRECTIONS) {
            purpleAerclouds.add(AetherIIBlocks.PURPLE_AERCLOUD.get().defaultBlockState().setValue(PurpleAercloudBlock.FACING, direction), 1);
        }
        
        register(context, COLD_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), new AercloudConfiguration(14, BlockStateProvider.simple(AetherIIBlocks.COLD_AERCLOUD.get().defaultBlockState())));
        register(context, GOLDEN_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), new AercloudConfiguration(10, BlockStateProvider.simple(AetherIIBlocks.GOLDEN_AERCLOUD.get().defaultBlockState())));
        register(context, BLUE_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), new AercloudConfiguration(14, BlockStateProvider.simple(AetherIIBlocks.BLUE_AERCLOUD.get().defaultBlockState())));
        register(context, GREEN_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), new AercloudConfiguration(14, BlockStateProvider.simple(AetherIIBlocks.GREEN_AERCLOUD.get().defaultBlockState())));
        register(context, PURPLE_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), new AercloudConfiguration(14, new WeightedStateProvider(purpleAerclouds)));
        register(context, STORM_AERCLOUD, AetherIIFeatures.AERCLOUD.get(), new AercloudConfiguration(12, BlockStateProvider.simple(AetherIIBlocks.STORM_AERCLOUD.get().defaultBlockState())));
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