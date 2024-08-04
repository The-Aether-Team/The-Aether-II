package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.registries.features.HighlandsConfiguredFeatures;
import com.aetherteam.aetherii.world.feature.modifier.predicate.ScanPredicate;
import com.aetherteam.aetherii.world.feature.modifier.predicate.SearchPredicate;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class HighlandsPlacedFeatures {
    // Surface
    public static final ResourceKey<PlacedFeature> SKYROOT_TWIGS = createKey("skyroot_twigs");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS = createKey("holystone_rocks");
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_BOULDER = createKey("mossy_holystone_boulder");
    public static final ResourceKey<PlacedFeature> FALLEN_SKYROOT_LOG = createKey("fallen_skyroot_log");


    // Vegetation
    public static final ResourceKey<PlacedFeature> GRASS_FIELD = createKey("grass_field");
    public static final ResourceKey<PlacedFeature> SMALL_GRASS_PATCH = createKey("small_grass_patch");
    public static final ResourceKey<PlacedFeature> MEDIUM_GRASS_PATCH = createKey("medium_grass_patch");
    public static final ResourceKey<PlacedFeature> LARGE_GRASS_PATCH = createKey("large_grass_patch");
    public static final ResourceKey<PlacedFeature> VALKYRIE_SPROUT_PATCH = createKey("valkyrie_sprout_patch");
    public static final ResourceKey<PlacedFeature> HIGHLANDS_BUSH_PATCH = createKey("highlands_bush_patch");
    public static final ResourceKey<PlacedFeature> HIGHLANDS_BUSH_PATCH_FIELD = createKey("highlands_bush_patch_field");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH = createKey("blueberry_bush_patch");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH = createKey("orange_tree_patch");

    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH = AetherIIPlacementUtils.createKey("highfields_flower_patch");
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH_FOREST = AetherIIPlacementUtils.createKey("highfields_flower_patch_forest");

    public static final ResourceKey<PlacedFeature> AETHER_GRASS_BONEMEAL = createKey("aether_grass_bonemeal");


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


    // Underground
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_FLOOR = createKey("coarse_aether_dirt_floor");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_CEILING = createKey("coarse_aether_dirt_ceiling");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_OVERHANG = createKey("coarse_aether_dirt_overhang");


    // Worldgen
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL = createKey("coast_quicksoil");
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL_SPARSE = createKey("coast_quicksoil_sparse");
    public static final ResourceKey<PlacedFeature> COAST_FERROSITE_SAND = createKey("coast_ferrosite_sand");
    public static final ResourceKey<PlacedFeature> COAST_ARCTIC_PACKED_ICE = createKey("coast_arctic_packed_ice");

    public static final ResourceKey<PlacedFeature> WATER_POND = createKey("water_pond");
    public static final ResourceKey<PlacedFeature> WATER_POND_UNDERGROUND = createKey("water_pond_underground");
    public static final ResourceKey<PlacedFeature> WATER_SPRING = createKey("water_spring");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE = createKey("noise_lake");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE_ARCTIC = createKey("noise_lake_arctic");

    public static final ResourceKey<PlacedFeature> FERROSITE_PILLAR = createKey("ferrosite_pillar");

    public static final ResourceKey<PlacedFeature> FERROSITE_SPIKE = createKey("ferrosite_spike");
    public static final ResourceKey<PlacedFeature> COASTAL_ARCTIC_ICE_SPIKE = createKey("coastal_arctic_ice_spike");
    public static final ResourceKey<PlacedFeature> ARCTIC_ICE_SPIKE_CLUSTER = createKey("arctic_ice_spike_cluster");

    public static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER_ARCTIC = createKey("freeze_top_layer_arctic");

    public static final ResourceKey<PlacedFeature> CLOUDBED = createKey("cloudbed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        bootstrapSurface(context);
        bootstrapVegetation(context);
        bootstrapTrees(context);
        bootstrapUnderground(context);
        bootstrapWorldgen(context);
    }

    public static void bootstrapSurface(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
                context,
                SKYROOT_TWIGS,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.SKYROOT_TWIGS),
                NoiseThresholdCountPlacement.of(0.4, 1, 0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(
                context,
                HOLYSTONE_ROCKS,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HOLYSTONE_ROCKS),
                NoiseThresholdCountPlacement.of(0.1, 0, 1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(
                context,
                MOSSY_HOLYSTONE_BOULDER,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MOSSY_HOLYSTONE_BOULDER),
                NoiseThresholdCountPlacement.of(0.2, 0, 1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable()),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                FALLEN_SKYROOT_LOG,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.FALLEN_SKYROOT_LOG),
                NoiseThresholdCountPlacement.of(0.0, 1, 2),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(1),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_DIRT), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)),
                BiomeFilter.biome()
        );
    }

    public static void bootstrapVegetation(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
                context,
                GRASS_FIELD,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.GRASS_FIELD),
                NoiseBasedCountPlacement.of(40, 5, 0.3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        register(
                context,
                SMALL_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.SMALL_GRASS_PATCH),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome()
        );
        register(
                context,
                MEDIUM_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MEDIUM_GRASS_PATCH),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 2)),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome()
        );
        register(
                context,
                LARGE_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.LARGE_GRASS_PATCH),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 3)),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome()
        );
        register(
                context,
                VALKYRIE_SPROUT_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.VALKYRIE_SPROUT_PATCH),
                NoiseThresholdCountPlacement.of(0.5, 0, 4),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome());
        register(context, HIGHLANDS_BUSH_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHLANDS_BUSH),
                NoiseThresholdCountPlacement.of(-0.1, 2, 0),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, HIGHLANDS_BUSH_PATCH_FIELD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHLANDS_BUSH),
                NoiseThresholdCountPlacement.of(-0.1, 2, 0),
                RarityFilter.onAverageOnceEvery(20),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, BLUEBERRY_BUSH_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.BLUEBERRY_BUSH),
                NoiseThresholdCountPlacement.of(0.1, 1, 0),
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, ORANGE_TREE_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORANGE_TREE),
                NoiseBasedCountPlacement.of(3, 10, 0),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), AetherIITags.Blocks.AETHER_PLANT_SURVIVES_ON), BlockPredicate.replaceable())),
                BiomeFilter.biome());

        register(context, HIGHFIELDS_FLOWER_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHFIELDS_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(-0.8, 5, 2),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome());
        register(context, HIGHFIELDS_FLOWER_PATCH_FOREST, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHFIELDS_FLOWER_PATCH),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());

        register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.AETHER_GRASS_BONEMEAL), PlacementUtils.isEmpty());
    }

    public static void bootstrapTrees(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Highfields
        register(context, FLOURISHING_FIELD_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FLOURISHING_FIELD),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, VERDANT_WOODS_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_VERDANT_WOODS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, SHROUDED_FOREST_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_SHROUDED_FOREST),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(150, 0.25F, 50)));
        register(context, SHIMMERING_BASIN_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_SHIMMERING_BASIN),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(5)));
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

    public static void bootstrapUnderground(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, COARSE_AETHER_DIRT_FLOOR, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COARSE_AETHER_DIRT_FLOOR),
                CountPlacement.of(45),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(96), VerticalAnchor.top(), 240)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );
        register(context, COARSE_AETHER_DIRT_CEILING, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COARSE_AETHER_DIRT_CEILING),
                CountPlacement.of(90),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.solid(), 12)),
                BiomeFilter.biome()
        );
        register(context, COARSE_AETHER_DIRT_OVERHANG, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COARSE_AETHER_DIRT_CEILING),
                NoiseBasedCountPlacement.of(50, 10, 0.0),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new ScanPredicate(Direction.DOWN, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 8)),
                BiomeFilter.biome()
        );
    }

    public static void bootstrapWorldgen(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, COAST_QUICKSOIL, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COAST_QUICKSOIL),
                CountPlacement.of(4),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );
        register(context, COAST_QUICKSOIL_SPARSE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COAST_QUICKSOIL),
                CountPlacement.of(2),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );
        register(context, COAST_FERROSITE_SAND, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COAST_FERROSITE_SAND),
                CountPlacement.of(4),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );
        register(context, COAST_ARCTIC_PACKED_ICE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COAST_ARCTIC_PACKED_ICE),
                CountPlacement.of(3),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(144)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );

        register(context, WATER_POND, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WATER_POND),
                RarityFilter.onAverageOnceEvery(25),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome());
        register(context, WATER_POND_UNDERGROUND, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WATER_POND),
                RarityFilter.onAverageOnceEvery(15),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 16),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5),
                BiomeFilter.biome());
        register(context, WATER_SPRING, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WATER_SPRING),
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.aboveBottom(256)),
                BiomeFilter.biome());
        register(context, NOISE_LAKE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.NOISE_LAKE), BiomeFilter.biome());
        register(context, NOISE_LAKE_ARCTIC, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.NOISE_LAKE_ARCTIC), BiomeFilter.biome());

        register(context, FERROSITE_PILLAR, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.FERROSITE_PILLAR),
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.absolute(200)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.AETHER_DIRT)), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        register(context, FERROSITE_SPIKE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.FERROSITE_SPIKE),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(112), VerticalAnchor.absolute(256)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), AetherIIBlocks.AETHER_GRASS_BLOCK.get())), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );
        register(context, COASTAL_ARCTIC_ICE_SPIKE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ARCTIC_ICE_SPIKE),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(112), VerticalAnchor.absolute(136)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get())), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );
        register(context, ARCTIC_ICE_SPIKE_CLUSTER, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ARCTIC_ICE_SPIKE_VARIANTS),
                NoiseBasedCountPlacement.of(10, 200.0, 0.0),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.absolute(224)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get())), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        register(context, FREEZE_TOP_LAYER_ARCTIC, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.FREEZE_TOP_LAYER_ARCTIC), BiomeFilter.biome());

        register(context, CLOUDBED, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.CLOUDBED), BiomeFilter.biome());
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
