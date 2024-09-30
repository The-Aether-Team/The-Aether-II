package com.aetherteam.aetherii.data.resources.registries.highlands;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.highlands.HighlandsPlacementBuilders;
import com.aetherteam.aetherii.world.feature.modifier.filter.ElevationFilter;
import com.aetherteam.aetherii.world.feature.modifier.filter.ImprovedLayerPlacementModifier;
import com.aetherteam.aetherii.world.feature.modifier.predicate.ScanPredicate;
import com.aetherteam.aetherii.world.feature.modifier.predicate.SearchPredicate;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class HighlandsPlacedFeatures {
    // Surface
    public static final ResourceKey<PlacedFeature> SKYROOT_TWIGS = createKey("skyroot_twigs");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS = createKey("holystone_rocks");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS_TUNDRA = createKey("holystone_rocks_tundra");
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_BOULDER = createKey("mossy_holystone_boulder");
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_BOULDER_TUNDRA = createKey("mossy_holystone_boulder_tundra");
    public static final ResourceKey<PlacedFeature> ICESTONE_BOULDER = createKey("icestone_boulder");
    public static final ResourceKey<PlacedFeature> FALLEN_SKYROOT_LOG = createKey("fallen_skyroot_log");
    public static final ResourceKey<PlacedFeature> FALLEN_WISPROOT_LOG = createKey("fallen_wisproot_log");
    public static final ResourceKey<PlacedFeature> MOA_NEST = createKey("moa_nest");


    // Vegetation
    public static final ResourceKey<PlacedFeature> GRASS_FIELD = createKey("grass_field");
    public static final ResourceKey<PlacedFeature> SMALL_GRASS_PATCH = createKey("small_grass_patch");
    public static final ResourceKey<PlacedFeature> MEDIUM_GRASS_PATCH = createKey("medium_grass_patch");
    public static final ResourceKey<PlacedFeature> LARGE_GRASS_PATCH = createKey("large_grass_patch");
    public static final ResourceKey<PlacedFeature> IRRADIATED_GRASS_PATCH = createKey("irradiated_grass_patch");
    public static final ResourceKey<PlacedFeature> VALKYRIE_SPROUT_PATCH = createKey("valkyrie_sprout_patch");
    public static final ResourceKey<PlacedFeature> HIGHLANDS_BUSH_PATCH = createKey("highlands_bush_patch");
    public static final ResourceKey<PlacedFeature> HIGHLANDS_BUSH_PATCH_FIELD = createKey("highlands_bush_patch_field");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH = createKey("blueberry_bush_patch");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH = createKey("orange_tree_patch");

    public static final ResourceKey<PlacedFeature> HIGHLANDS_FLOWER_PATCH = createKey("highlands_flower_patch");
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH = createKey("highfields_flower_patch");
    public static final ResourceKey<PlacedFeature> MAGNETIC_FLOWER_PATCH = createKey("magnetic_flower_patch");
    public static final ResourceKey<PlacedFeature> ARCTIC_FLOWER_PATCH = createKey("arctic_flower_patch");
    public static final ResourceKey<PlacedFeature> MAGNETIC_SHROOM_PATCH = createKey("magnetic_shroom_patch");
    public static final ResourceKey<PlacedFeature> BONUS_MAGNETIC_SHROOM_PATCH = createKey("bonus_magnetic_shroom_patch");

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
    public static final ResourceKey<PlacedFeature> GLISTENING_SWAMP_TREES = createKey("glistening_swamp_trees");
    public static final ResourceKey<PlacedFeature> GLISTENING_SWAMP_TREES_SUNKEN = createKey("glistening_swamp_trees_sunken");
    public static final ResourceKey<PlacedFeature> VIOLET_HIGHWOODS_TREES = createKey("violet_highwoods_trees");

    // Arctic
    public static final ResourceKey<PlacedFeature> FRIGID_SIERRA_TREES = createKey("frigid_sierra_trees");
    public static final ResourceKey<PlacedFeature> ENDURING_WOODLAND_TREES = createKey("enduring_woodland_trees");
    public static final ResourceKey<PlacedFeature> FROZEN_LAKES_TREES = createKey("frozen_lakes_trees");

    // Irradiated
    public static final ResourceKey<PlacedFeature> CONTAMINATED_JUNGLE_TREES = createKey("contaminated_jungle_trees");
    public static final ResourceKey<PlacedFeature> BATTLEGROUND_WASTES_TREES = createKey("battleground_wastes_trees");


    // Underground
    public static final ResourceKey<PlacedFeature> GRASS_AND_DIRT_FLOOR = createKey("grass_and_dirt_floor");
    public static final ResourceKey<PlacedFeature> ENCHANTED_GRASS_AND_DIRT_FLOOR = createKey("enchanted_grass_and_dirt_floor");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_FLOOR = createKey("coarse_aether_dirt_floor");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_CEILING = createKey("coarse_aether_dirt_ceiling");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_FROSTED_CEILING = createKey("coarse_aether_dirt_frosted_ceiling");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_OVERHANG = createKey("coarse_aether_dirt_overhang");
    public static final ResourceKey<PlacedFeature> ICE_OVERHANG = createKey("ice_overhang");
    public static final ResourceKey<PlacedFeature> EXPOSED_BRYALINN_MOSS_COVER = createKey("exposed_bryalinn_moss_cover");
    public static final ResourceKey<PlacedFeature> SWAMP_BRYALINN_MOSS_COVER = createKey("swamp_bryalinn_moss_cover");
    public static final ResourceKey<PlacedFeature> EXPOSED_SHAYELINN_MOSS_COVER = createKey("exposed_shayelinn_moss_cover");

    public static final ResourceKey<PlacedFeature> ORE_SCATTERGLASS = createKey("ore_scatterglass");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE = createKey("ore_icestone");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE_SMALL = createKey("ore_icestone_small");
    public static final ResourceKey<PlacedFeature> ORE_AGIOSITE = createKey("ore_agiosite");

    public static final ResourceKey<PlacedFeature> ORE_HOLYSTONE_QUARTZ = createKey("ore_holystone_quartz");
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM = createKey("ore_ambrosium");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE = createKey("ore_zanite");
    public static final ResourceKey<PlacedFeature> ORE_GLINT = createKey("ore_glint");
    public static final ResourceKey<PlacedFeature> ORE_ARKENIUM = createKey("ore_arkenium");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_BURIED = createKey("ore_gravitite_buried");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE = createKey("ore_gravitite");
    public static final ResourceKey<PlacedFeature> ORE_CORROBONITE = createKey("ore_corrobonite");


    // Worldgen
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL = createKey("coast_quicksoil");
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL_SPARSE = createKey("coast_quicksoil_sparse");
    public static final ResourceKey<PlacedFeature> COAST_FERROSITE_SAND = createKey("coast_ferrosite_sand");
    public static final ResourceKey<PlacedFeature> COAST_ARCTIC_PACKED_ICE = createKey("coast_arctic_packed_ice");

    public static final ResourceKey<PlacedFeature> WATER_POND = createKey("water_pond");
    public static final ResourceKey<PlacedFeature> WATER_POND_UNDERGROUND = createKey("water_pond_underground");
    public static final ResourceKey<PlacedFeature> WATER_POND_TUNDRA = createKey("water_pond_tundra");
    public static final ResourceKey<PlacedFeature> WATER_SPRING = createKey("water_spring");
    public static final ResourceKey<PlacedFeature> BONUS_WATER_SPRING = createKey("bonus_water_spring");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE = createKey("noise_lake");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE_ARCTIC = createKey("noise_lake_arctic");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE_SWAMP = createKey("noise_lake_swamp");

    public static final ResourceKey<PlacedFeature> FERROSITE_PILLAR = createKey("ferrosite_pillar");

    public static final ResourceKey<PlacedFeature> FERROSITE_SPIKE = createKey("ferrosite_spike");
    public static final ResourceKey<PlacedFeature> COASTAL_ARCTIC_ICE_SPIKE = createKey("coastal_arctic_ice_spike");
    public static final ResourceKey<PlacedFeature> ARCTIC_ICE_SPIKE_CLUSTER = createKey("arctic_ice_spike_cluster");

    public static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER_ARCTIC = createKey("freeze_top_layer_arctic");
    public static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER_TUNDRA = createKey("freeze_top_layer_tundra");

    public static final ResourceKey<PlacedFeature> CLOUDBED = createKey("cloudbed");


    // Air
    public static final ResourceKey<PlacedFeature> HIGH_STORM_AERCLOUD = createKey("high_storm_aercloud");
    public static final ResourceKey<PlacedFeature> HIGH_GREEN_AERCLOUD = createKey("high_green_aercloud");
    public static final ResourceKey<PlacedFeature> HIGH_PURPLE_AERCLOUD = createKey("high_purple_aercloud");

    public static final ResourceKey<PlacedFeature> MIDDLE_COLD_AERCLOUD = createKey("middle_cold_aercloud");
    public static final ResourceKey<PlacedFeature> MIDDLE_STORM_AERCLOUD = createKey("middle_storm_aercloud");

    public static final ResourceKey<PlacedFeature> SURFACE_COLD_AERCLOUD = createKey("surface_cold_aercloud");
    public static final ResourceKey<PlacedFeature> SURFACE_GOLDEN_AERCLOUD = createKey("surface_golden_aercloud");
    public static final ResourceKey<PlacedFeature> SURFACE_BLUE_AERCLOUD = createKey("surface_blue_aercloud");
    public static final ResourceKey<PlacedFeature> SURFACE_GREEN_AERCLOUD = createKey("surface_green_aercloud");
    public static final ResourceKey<PlacedFeature> SURFACE_PURPLE_AERCLOUD = createKey("surface_purple_aercloud");

    public static final ResourceKey<PlacedFeature> LOWER_STORM_AERCLOUD = createKey("lower_storm_aercloud");
    public static final ResourceKey<PlacedFeature> LOWER_BLUE_AERCLOUD = createKey("lower_blue_aercloud");
    public static final ResourceKey<PlacedFeature> LOWER_GREEN_AERCLOUD = createKey("lower_green_aercloud");
    public static final ResourceKey<PlacedFeature> LOWER_PURPLE_AERCLOUD = createKey("lower_purple_aercloud");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        bootstrapSurface(context);
        bootstrapVegetation(context);
        bootstrapTrees(context);
        bootstrapUnderground(context);
        bootstrapWorldgen(context);
        bootstrapAir(context);
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
                HOLYSTONE_ROCKS_TUNDRA,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HOLYSTONE_ROCKS),
                NoiseThresholdCountPlacement.of(0.1, 1, 2),
                RarityFilter.onAverageOnceEvery(2),
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
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.BOULDER_SURVIVES_ON))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                MOSSY_HOLYSTONE_BOULDER_TUNDRA,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MOSSY_HOLYSTONE_BOULDER),
                NoiseThresholdCountPlacement.of(0.2, 1, 2),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.BOULDER_SURVIVES_ON))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                ICESTONE_BOULDER,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ICESTONE_BOULDER),
                NoiseThresholdCountPlacement.of(0.0, 1, 0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                new ElevationFilter(VerticalAnchor.aboveBottom(192), VerticalAnchor.top()),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.BOULDER_SURVIVES_ON))),
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
        register(
                context,
                FALLEN_WISPROOT_LOG,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.FALLEN_WISPROOT_LOG),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_DIRT), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)),
                BiomeFilter.biome()
        );
        register(context, MOA_NEST, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MOA_NEST),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(128), VerticalAnchor.absolute(200)),
                PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
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
                ImprovedLayerPlacementModifier.of(Heightmap.Types.WORLD_SURFACE_WG, UniformInt.of(0, 1), 4),
                BiomeFilter.biome()
        );
        register(
                context,
                SMALL_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.SMALL_GRASS_PATCH),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UniformInt.of(0, 1), 4),
                BiomeFilter.biome()
        );
        register(
                context,
                MEDIUM_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MEDIUM_GRASS_PATCH),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UniformInt.of(0, 2), 4),
                BiomeFilter.biome()
        );
        register(
                context,
                LARGE_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.LARGE_GRASS_PATCH),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UniformInt.of(0, 3), 4),
                BiomeFilter.biome()
        );
        register(
                context,
                IRRADIATED_GRASS_PATCH,
                configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.IRRADIATED_GRASS_PATCH),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UniformInt.of(2, 6), 4),
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
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UniformInt.of(0, 1), 4),
                BiomeFilter.biome());
        register(context, HIGHLANDS_BUSH_PATCH_FIELD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHLANDS_BUSH),
                NoiseThresholdCountPlacement.of(-0.1, 2, 0),
                RarityFilter.onAverageOnceEvery(20),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UniformInt.of(0, 1), 4),
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

        register(context, HIGHLANDS_FLOWER_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHLANDS_FLOWER_PATCH),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, HIGHFIELDS_FLOWER_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.HIGHFIELDS_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(0.8, 1, 3),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, MAGNETIC_FLOWER_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MAGNETIC_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(0.8, 1, 3),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, ARCTIC_FLOWER_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ARCTIC_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(0.8, 1, 3),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome());
        register(context, MAGNETIC_SHROOM_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MAGNETIC_SHROOM_PATCH),
                CountPlacement.of(UniformInt.of(0, 12)),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome());
        register(context, BONUS_MAGNETIC_SHROOM_PATCH, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.MAGNETIC_SHROOM_PATCH),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome());

        register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.AETHER_GRASS_BONEMEAL), PlacementUtils.isEmpty());
    }

    public static void bootstrapTrees(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Highfields
        register(context, FLOURISHING_FIELD_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FLOURISHING_FIELD),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, VERDANT_WOODS_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_VERDANT_WOODS),
                HighlandsPlacementBuilders.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, SHROUDED_FOREST_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_SHROUDED_FOREST),
                HighlandsPlacementBuilders.treePlacement(PlacementUtils.countExtra(150, 0.25F, 50)));
        register(context, SHIMMERING_BASIN_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_SHIMMERING_BASIN),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(5)));
        register(context, SHIMMERING_BASIN_TREES_SUNKEN, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WISPROOT),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(2),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AetherIIBlocks.WISPROOT_SAPLING.get().defaultBlockState(), BlockPos.ZERO)));

        // Magnetic
        register(context, MAGNETIC_SCAR_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_MAGNETIC_SCAR),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        register(context, TURQUOISE_FOREST_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_TURQUOISE_FOREST),
                HighlandsPlacementBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        register(context, GLISTENING_SWAMP_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_GLISTENING_SWAMP),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(10)));
        register(context, GLISTENING_SWAMP_TREES_SUNKEN, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.SWAMP_GREATROOT),
                RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(3),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AetherIIBlocks.GREATROOT_SAPLING.get().defaultBlockState(), BlockPos.ZERO)));
        register(context, VIOLET_HIGHWOODS_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_VIOLET_HIGHWOODS),
                HighlandsPlacementBuilders.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

        // Arctic
        register(context, FRIGID_SIERRA_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FRIGID_SIERRA),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(4))); //16
        register(context, ENDURING_WOODLAND_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_ENDURING_WOODLANDS),
                HighlandsPlacementBuilders.treePlacement(PlacementUtils.countExtra(20, 0.1F, 4)));
        register(context, FROZEN_LAKES_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_BIOME_FROZEN_LAKES),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(1)));

        // Irradiated
        register(context, CONTAMINATED_JUNGLE_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_IRRADIATED),
                HighlandsPlacementBuilders.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, BATTLEGROUND_WASTES_TREES, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.TREES_IRRADIATED),
                HighlandsPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(3)));
    }

    public static void bootstrapUnderground(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, GRASS_AND_DIRT_FLOOR, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.GRASS_AND_DIRT_FLOOR),
                CountPlacement.of(65),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 220)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, -32, 2),
                BiomeFilter.biome()
        );
        register(context, ENCHANTED_GRASS_AND_DIRT_FLOOR, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ENCHANTED_GRASS_AND_DIRT_FLOOR),
                CountPlacement.of(80),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(80), VerticalAnchor.top(), 250)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, -32, 2),
                BiomeFilter.biome()
        );
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
        register(context, COARSE_AETHER_DIRT_FROSTED_CEILING, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COARSE_AETHER_DIRT_FROSTED_CEILING),
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
        register(context, ICE_OVERHANG, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ICE_CEILING),
                CountPlacement.of(4),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new ScanPredicate(Direction.DOWN, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 8)),
                BiomeFilter.biome()
        );
        register(context, EXPOSED_BRYALINN_MOSS_COVER, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.BRYALINN_MOSS_FLOOR),
                NoiseBasedCountPlacement.of(35, 50, 0.0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                new ElevationFilter(VerticalAnchor.bottom(), VerticalAnchor.belowTop(276)),
                BiomeFilter.biome()
        );
        register(context, SWAMP_BRYALINN_MOSS_COVER, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.BRYALINN_MOSS_FLOOR_SWAMP),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(126), VerticalAnchor.aboveBottom(180))),
                new ElevationFilter(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        register(context, EXPOSED_SHAYELINN_MOSS_COVER, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.SHAYELINN_MOSS_FLOOR),
                NoiseBasedCountPlacement.of(8, 30, 0.0),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                new ElevationFilter(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );

        register(context, ORE_SCATTERGLASS, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_SCATTERGLASS),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
        register(context, ORE_ICESTONE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_ICESTONE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
        register(context, ORE_ICESTONE_SMALL, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_ICESTONE_SMALL),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
        register(context, ORE_AGIOSITE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_AGIOSITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-96), VerticalAnchor.aboveBottom(96))));

        register(context, ORE_HOLYSTONE_QUARTZ, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_HOLYSTONE_QUARTZ),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(1, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(128), VerticalAnchor.top())));
        register(context, ORE_AMBROSIUM, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_AMBROSIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(20, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(24), VerticalAnchor.top(), 96))));
         register(context, ORE_ZANITE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_ZANITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(64), VerticalAnchor.aboveBottom(170))));
        register(context, ORE_GLINT, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_GLINT),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(114))));
         register(context, ORE_ARKENIUM, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_ARKENIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96), 48))));
        register(context, ORE_GRAVITITE_BURIED, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_GRAVITITE_BURIED),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(72), 20))));
        register(context, ORE_GRAVITITE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_GRAVITITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(5, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(72), 20))));
        register(context, ORE_CORROBONITE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.ORE_CORROBONITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(60), 25))));
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
        register(context, WATER_POND_TUNDRA, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WATER_POND_TUNDRA),
                RarityFilter.onAverageOnceEvery(2),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome());
        register(context, WATER_SPRING, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WATER_SPRING),
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.aboveBottom(256)),
                BiomeFilter.biome());
        register(context, BONUS_WATER_SPRING, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.WATER_SPRING),
                CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(128), VerticalAnchor.aboveBottom(200)),
                BiomeFilter.biome());
        register(context, NOISE_LAKE, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.NOISE_LAKE), BiomeFilter.biome());
        register(context, NOISE_LAKE_ARCTIC, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.NOISE_LAKE_ARCTIC), BiomeFilter.biome());
        register(context, NOISE_LAKE_SWAMP, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.NOISE_LAKE_SWAMP), BiomeFilter.biome());

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
        register(context, FREEZE_TOP_LAYER_TUNDRA, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.FREEZE_TOP_LAYER_TUNDRA), BiomeFilter.biome());

        register(context, CLOUDBED, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.CLOUDBED), BiomeFilter.biome());
    }

    public static void bootstrapAir(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, HIGH_STORM_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.STORM_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(288), VerticalAnchor.aboveBottom(320)),
                RarityFilter.onAverageOnceEvery(72),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, HIGH_GREEN_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.GREEN_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(256), VerticalAnchor.aboveBottom(304)),
                RarityFilter.onAverageOnceEvery(72),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, HIGH_PURPLE_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.PURPLE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(256), VerticalAnchor.aboveBottom(304)),
                RarityFilter.onAverageOnceEvery(72),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

        register(context, MIDDLE_COLD_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COLD_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(208), VerticalAnchor.aboveBottom(256)),
                RarityFilter.onAverageOnceEvery(72),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, MIDDLE_STORM_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.STORM_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(208), VerticalAnchor.aboveBottom(256)),
                RarityFilter.onAverageOnceEvery(80),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

        register(context, SURFACE_COLD_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.COLD_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(48),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, SURFACE_GOLDEN_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.GOLDEN_AERCLOUD),
                HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(70),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, SURFACE_BLUE_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.BLUE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(64),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, SURFACE_GREEN_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.GREEN_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(64),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, SURFACE_PURPLE_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.PURPLE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(64),
                InSquarePlacement.spread(),
                BiomeFilter.biome());

        register(context, LOWER_STORM_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.STORM_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(64), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(48),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, LOWER_BLUE_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.BLUE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(48),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, LOWER_GREEN_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.GREEN_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(48),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
        register(context, LOWER_PURPLE_AERCLOUD, configuredFeatures.getOrThrow(HighlandsConfiguredFeatures.PURPLE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(48),
                InSquarePlacement.spread(),
                BiomeFilter.biome());
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
