package com.aetherteam.aetherii.data.resources.registries.holyisles;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.worldgen.holyisles.HolyIslesPlacementBuilders;
import com.aetherteam.aetherii.world.feature.modifier.filter.ElevationFilter;
import com.aetherteam.aetherii.world.feature.modifier.filter.ImprovedLayerPlacementModifier;
import com.aetherteam.aetherii.world.feature.modifier.filter.LakePlacementModifier;
import com.aetherteam.aetherii.world.feature.modifier.filter.StructureBlacklistFilter;
import com.aetherteam.aetherii.world.feature.modifier.predicate.MossyPredicate;
import com.aetherteam.aetherii.world.feature.modifier.predicate.ScanPredicate;
import com.aetherteam.aetherii.world.feature.modifier.predicate.SearchPredicate;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.HasSturdyFacePredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class HolyIslesPlacedFeatures {
    // Surface
    public static final ResourceKey<PlacedFeature> SKYROOT_TWIGS = createKey("skyroot_twigs");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS = createKey("holystone_rocks");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS_FLOOR = createKey("holystone_rocks_floor");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS_TUNDRA = createKey("holystone_rocks_tundra");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS_UNDERWATER = createKey("holystone_rocks_underwater");
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_BOULDER = createKey("mossy_holystone_boulder");
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_BOULDER_TUNDRA = createKey("mossy_holystone_boulder_tundra");
    public static final ResourceKey<PlacedFeature> UNDERWATER_MOSSY_HOLYSTONE_BOULDER = createKey("underwater_mossy_holystone_boulder");
    public static final ResourceKey<PlacedFeature> ICESTONE_BOULDER = createKey("icestone_boulder");
    public static final ResourceKey<PlacedFeature> UNDERWATER_ARCTIC_HOLYSTONE_BOULDER = createKey("underwater_arctic_holystone_boulder");
    public static final ResourceKey<PlacedFeature> FALLEN_SKYROOT_LOG = createKey("fallen_skyroot_log");
    public static final ResourceKey<PlacedFeature> FALLEN_WISPROOT_LOG = createKey("fallen_wisproot_log");
    public static final ResourceKey<PlacedFeature> MOA_NEST = createKey("moa_nest");


    // Vegetation
    public static final ResourceKey<PlacedFeature> GRASS_FIELD = createKey("grass_field");
    public static final ResourceKey<PlacedFeature> SMALL_GRASS_PATCH = createKey("small_grass_patch");
    public static final ResourceKey<PlacedFeature> MEDIUM_GRASS_PATCH = createKey("medium_grass_patch");
    public static final ResourceKey<PlacedFeature> LARGE_GRASS_PATCH = createKey("large_grass_patch");
    public static final ResourceKey<PlacedFeature> IRRADIATED_GRASS_PATCH = createKey("irradiated_grass_patch");
    public static final ResourceKey<PlacedFeature> VEGETATION_GRASS_PATCH = createKey("vegetation_grass_patch");
    public static final ResourceKey<PlacedFeature> VALKYRIE_SPROUT_PATCH = createKey("valkyrie_sprout_patch");
    public static final ResourceKey<PlacedFeature> BUSH_FERNS_PATCH = createKey("bush_ferns_patch");
    public static final ResourceKey<PlacedFeature> AETHER_BUSH_HEDGE_DEFAULT = createKey("aether_bush_hedge_default");
    public static final ResourceKey<PlacedFeature> AETHER_BUSH_HEDGE_FIELD = createKey("aether_bush_hedge_field");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH = createKey("blueberry_bush_patch");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH_DEFAULT = createKey("blueberry_bush_patch_default");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH_RARE = createKey("blueberry_bush_patch_rare");
    public static final ResourceKey<PlacedFeature> BLUEBERRY_BUSH_PATCH_IRRADIATED = createKey("blueberry_bush_patch_irradiated");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH = createKey("orange_tree_patch");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH_RARE = createKey("orange_tree_patch_rare");
    public static final ResourceKey<PlacedFeature> ORANGE_TREE_PATCH_IRRADIATED = createKey("orange_tree_patch_irradiated");
    public static final ResourceKey<PlacedFeature> BRETTL_PATCH_LAKE = createKey("brettl_patch_lake");
    public static final ResourceKey<PlacedFeature> BRETTL_PATCH_IRRADIATED = createKey("brettl_patch_irradiated");

    public static final ResourceKey<PlacedFeature> HOLY_ISLES_FLOWER_PATCH = createKey("holy_isles_flower_patch");
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_PATCH = createKey("highfields_flower_patch");
    public static final ResourceKey<PlacedFeature> HIGHFIELDS_FLOWER_FIELD = createKey("highfields_flower_field");
    public static final ResourceKey<PlacedFeature> MAGNETIC_FLOWER_PATCH = createKey("magnetic_flower_patch");
    public static final ResourceKey<PlacedFeature> ARCTIC_FLOWER_PATCH = createKey("arctic_flower_patch");
    public static final ResourceKey<PlacedFeature> MAGNETIC_SHROOM_PATCH = createKey("magnetic_shroom_patch");
    public static final ResourceKey<PlacedFeature> BONUS_MAGNETIC_SHROOM_PATCH = createKey("bonus_magnetic_shroom_patch");
    public static final ResourceKey<PlacedFeature> MYCELIAL_MAGNETIC_SHROOM_PATCH = createKey("mycelial_magnetic_shroom_patch");
    public static final ResourceKey<PlacedFeature> BRYALINN_FLOWER_PATCH = createKey("bryalinn_flower_patch");

    public static final ResourceKey<PlacedFeature> SHORT_ARILUM = createKey("short_arilum");
    public static final ResourceKey<PlacedFeature> ARILUM = createKey("arilum");
    public static final ResourceKey<PlacedFeature> BLOOMING_ARILUM = createKey("blooming_arilum");
    public static final ResourceKey<PlacedFeature> MIXED_ARILUM = createKey("mixed_arilum");
    public static final ResourceKey<PlacedFeature> POND_ARILUM = createKey("pond_arilum");

    public static final ResourceKey<PlacedFeature> TREE_MOSS_COVER = createKey("tree_moss_cover");

    public static final ResourceKey<PlacedFeature> AETHER_GRASS_BONEMEAL = createKey("aether_grass_bonemeal");
    public static final ResourceKey<PlacedFeature> ARILUM_BONEMEAL = createKey("arilum_bonemeal");


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
    public static final ResourceKey<PlacedFeature> GLISTENING_SWAMP_MAGNETIC_SHROOMS = createKey("glistening_swamp_magnetic_shrooms");
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
    public static final ResourceKey<PlacedFeature> SMALL_MYCELIUM_FLOOR = createKey("small_mycelium_floor");
    public static final ResourceKey<PlacedFeature> BIG_MYCELIUM_FLOOR = createKey("big_mycelium_floor");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_FLOOR = createKey("coarse_aether_dirt_floor");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_CEILING = createKey("coarse_aether_dirt_ceiling");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_FROSTED_CEILING = createKey("coarse_aether_dirt_frosted_ceiling");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_OVERHANG = createKey("coarse_aether_dirt_overhang");
    public static final ResourceKey<PlacedFeature> ICE_OVERHANG = createKey("ice_overhang");
    public static final ResourceKey<PlacedFeature> POINTED_HOLYSTONE = createKey("pointed_holystone");
    public static final ResourceKey<PlacedFeature> POINTED_ICHORITE = createKey("pointed_ichorite");
    public static final ResourceKey<PlacedFeature> BRYALINN_MOSS_CARPET_PATCH = createKey("bryalinn_moss_carpet_patch");
    public static final ResourceKey<PlacedFeature> BRYALINN_MOSS_FLOWER_PATCH = createKey("bryalinn_moss_flower_patch");
    public static final ResourceKey<PlacedFeature> SHAYELINN_MOSS_CARPET_PATCH = createKey("shayelinn_moss_carpet_patch");
    public static final ResourceKey<PlacedFeature> AMBRELINN_MOSS_CARPET_PATCH = createKey("ambrelinn_moss_carpet_patch");
    public static final ResourceKey<PlacedFeature> EXPOSED_BRYALINN_MOSS_COVER = createKey("exposed_bryalinn_moss_cover");
    public static final ResourceKey<PlacedFeature> SWAMP_BRYALINN_MOSS_COVER = createKey("swamp_bryalinn_moss_cover");
    public static final ResourceKey<PlacedFeature> EXPOSED_SHAYELINN_MOSS_COVER = createKey("exposed_shayelinn_moss_cover");

    public static final ResourceKey<PlacedFeature> UNSTABLE_HOLYSTONE = createKey("unstable_holystone");
    public static final ResourceKey<PlacedFeature> UNSTABLE_UNDERSHALE = createKey("unstable_undershale");

    public static final ResourceKey<PlacedFeature> ALKAHEST_POOL_RARE = createKey("alkahest_pool_rare");
    public static final ResourceKey<PlacedFeature> ALKAHEST_POOL = createKey("alkahest_pool");

    public static final ResourceKey<PlacedFeature> ORE_SCATTERGLASS = createKey("ore_scatterglass");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE = createKey("ore_icestone");
    public static final ResourceKey<PlacedFeature> ORE_ICESTONE_SMALL = createKey("ore_icestone_small");
    public static final ResourceKey<PlacedFeature> ORE_AGIOSITE = createKey("ore_agiosite");
    public static final ResourceKey<PlacedFeature> ORE_AGIOSITE_SMALL = createKey("ore_agiosite_small");

    public static final ResourceKey<PlacedFeature> ORE_HOLYSTONE_QUARTZ = createKey("ore_holystone_quartz");
    public static final ResourceKey<PlacedFeature> ORE_AMBROSIUM = createKey("ore_ambrosium");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE = createKey("ore_zanite");
    public static final ResourceKey<PlacedFeature> ORE_ZANITE_MOUNTAIN = createKey("ore_zanite_mountain");
    public static final ResourceKey<PlacedFeature> ORE_GLINT = createKey("ore_glint");
    public static final ResourceKey<PlacedFeature> ORE_ARKENIUM = createKey("ore_arkenium");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE_BURIED = createKey("ore_gravitite_buried");
    public static final ResourceKey<PlacedFeature> ORE_GRAVITITE = createKey("ore_gravitite");
    public static final ResourceKey<PlacedFeature> ORE_CORROBONITE = createKey("ore_corrobonite");

    public static final ResourceKey<PlacedFeature> ORE_HESTVEIL_OPEN = createKey("ore_hestveil_open");
    public static final ResourceKey<PlacedFeature> ORE_HESTVEIL_BURIED = createKey("ore_hestveil_buried");


    // Worldgen
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_SURFACE = createKey("coarse_aether_dirt_surface");
    public static final ResourceKey<PlacedFeature> DISK_BRYALINN_MOSS = createKey("disk_bryalinn_moss");
    public static final ResourceKey<PlacedFeature> LAKE_DISK_BRYALINN_MOSS = createKey("lake_disk_bryalinn_moss");

    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL = createKey("coast_quicksoil");
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL_SPARSE = createKey("coast_quicksoil_sparse");
    public static final ResourceKey<PlacedFeature> COAST_FERROSITE_SAND = createKey("coast_ferrosite_sand");
    public static final ResourceKey<PlacedFeature> COAST_FERROSITE_PILLAR = createKey("coast_ferrosite_pillar");
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

    public static final ResourceKey<PlacedFeature> CRATER = createKey("crater");

    public static final ResourceKey<PlacedFeature> CLOUDBED = createKey("cloudbed");


    // Dungeon
    public static final ResourceKey<PlacedFeature> BRYALINN_MOSS_COVER_STRUCTURE = createKey("bryalinn_moss_cover_structure");
    public static final ResourceKey<PlacedFeature> LARGE_SHELF_ROTSHROOM = createKey("large_shelf_rotshroom");
    public static final ResourceKey<PlacedFeature> LARGE_SHELF_ROTSHROOM_UNDERGROUND = createKey("large_shelf_rotshroom_underground");
    public static final ResourceKey<PlacedFeature> ROTSHROOM_PATCH = createKey("rotshroom_patch");
    public static final ResourceKey<PlacedFeature> COARSE_AETHER_DIRT_DUNGEON = createKey("coarse_aether_dirt_dungeon");
    public static final ResourceKey<PlacedFeature> UNDERGROWTH_PATCH = createKey("undergrowth_patch");
    public static final ResourceKey<PlacedFeature> INFECTED_GUARDIAN_TREE_ENTRANCE_COVER = createKey("infected_guardian_tree_entrance_cover");
    public static final ResourceKey<PlacedFeature> INFECTED_GUARDIAN_TREE_STAIRCASE_COVER = createKey("infected_guardian_tree_staircase_cover");
    public static final ResourceKey<PlacedFeature> INFECTED_GUARDIAN_TREE_LOBBY_COVER = createKey("infected_guardian_tree_lobby_cover");
    public static final ResourceKey<PlacedFeature> INFECTED_GUARDIAN_TREE_BOSS_ROOM_COVER = createKey("infected_guardian_tree_boss_room_cover");


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
        bootstrapDungeon(context);
    }

    public static void bootstrapSurface(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, SKYROOT_TWIGS,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SKYROOT_TWIGS),
                NoiseThresholdCountPlacement.of(0.4, 1, 0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome(),
                CountPlacement.of(2),
                RandomOffsetPlacement.ofTriangle(1, 1),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_SKYROOT_TWIG), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, HOLYSTONE_ROCKS,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.HOLYSTONE_ROCKS),
                NoiseThresholdCountPlacement.of(0.1, 0, 1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome(),
                CountPlacement.of(4),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_HOLYSTONE_ROCK), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, HOLYSTONE_ROCKS_FLOOR,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.HOLYSTONE_ROCKS),
                CountPlacement.of(4),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_HOLYSTONE_ROCK), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, HOLYSTONE_ROCKS_TUNDRA,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.HOLYSTONE_ROCKS),
                NoiseThresholdCountPlacement.of(0.1, 1, 2),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome(),
                CountPlacement.of(4),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_HOLYSTONE_ROCK), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, HOLYSTONE_ROCKS_UNDERWATER,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.UNDERWATER_HOLYSTONE_ROCKS),
                CountPlacement.of(4),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_HOLYSTONE_ROCK), BlockPredicate.matchesBlocks(Blocks.WATER)))
        );
        register(
                context,
                MOSSY_HOLYSTONE_BOULDER,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MOSSY_HOLYSTONE_BOULDER),
                NoiseThresholdCountPlacement.of(0.2, 0, 1),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BOULDER))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                MOSSY_HOLYSTONE_BOULDER_TUNDRA,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MOSSY_HOLYSTONE_BOULDER),
                NoiseThresholdCountPlacement.of(0.2, 1, 2),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BOULDER))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                UNDERWATER_MOSSY_HOLYSTONE_BOULDER,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.UNDERWATER_MOSSY_HOLYSTONE_BOULDER),
                new LakePlacementModifier(),
                RarityFilter.onAverageOnceEvery(40),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BOULDER), BlockPredicate.matchesBlocks(Blocks.WATER))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                ICESTONE_BOULDER,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ICESTONE_BOULDER),
                NoiseThresholdCountPlacement.of(0.0, 1, 0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                new ElevationFilter(VerticalAnchor.aboveBottom(192), VerticalAnchor.top()),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BOULDER))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                UNDERWATER_ARCTIC_HOLYSTONE_BOULDER,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.UNDERWATER_ARCTIC_HOLYSTONE_BOULDER),
                new LakePlacementModifier(),
                RarityFilter.onAverageOnceEvery(60),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BOULDER), BlockPredicate.matchesBlocks(Blocks.WATER))),
                RandomOffsetPlacement.vertical(UniformInt.of(0, 1)),
                BiomeFilter.biome()
        );
        register(
                context,
                FALLEN_SKYROOT_LOG,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.FALLEN_SKYROOT_LOG),
                NoiseThresholdCountPlacement.of(0.0, 1, 2),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(1),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_GROUND_BLOCKS), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)),
                BiomeFilter.biome()
        );
        register(
                context,
                FALLEN_WISPROOT_LOG,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.FALLEN_WISPROOT_LOG),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.AETHER_GROUND_BLOCKS), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)),
                BiomeFilter.biome()
        );
        register(context, MOA_NEST, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MOA_NEST),
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

        register(context, GRASS_FIELD,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.GRASS_FIELD),
                CountPlacement.of(24),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.WORLD_SURFACE_WG, UniformInt.of(0, 1), 4),
                BiomeFilter.biome(),
                CountPlacement.of(256),
                RandomOffsetPlacement.ofTriangle(12, 4),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, SMALL_GRASS_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SMALL_GRASS),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(3, 6), 4),
                BiomeFilter.biome(),
                CountPlacement.of(80),
                RandomOffsetPlacement.ofTriangle(4, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, MEDIUM_GRASS_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MEDIUM_GRASS),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(2, 4), 4),
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, LARGE_GRASS_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.LARGE_GRASS),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 3), 4),
                BiomeFilter.biome(),
                CountPlacement.of(48),
                RandomOffsetPlacement.ofTriangle(8, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, IRRADIATED_GRASS_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.IRRADIATED_GRASS),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(2, 6), 4),
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(8, 4),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, VEGETATION_GRASS_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MEDIUM_GRASS),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, VALKYRIE_SPROUT_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.VALKYRIE_SPROUT),
                NoiseThresholdCountPlacement.of(0.5, 0, 4),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(160),
                RandomOffsetPlacement.ofTriangle(4, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.ONLY_IN_AIR_PREDICATE))
        );
        register(context, BUSH_FERNS_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.AETHER_FERN),
                CountPlacement.of(144),
                RandomOffsetPlacement.ofTriangle(5, 4),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, AETHER_BUSH_HEDGE_DEFAULT,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.AETHER_BUSH_HEDGE),
                NoiseThresholdCountPlacement.of(0.1, 1, 0),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome()
        );
        register(context, AETHER_BUSH_HEDGE_FIELD,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.AETHER_BUSH_HEDGE),
                NoiseThresholdCountPlacement.of(0.1, 3, 0),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome()
        );
        register(context, BLUEBERRY_BUSH_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLUEBERRY_BUSH),
                CountPlacement.of(96),
                RandomOffsetPlacement.ofTriangle(2, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.north())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.east())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.south())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.west()))
        );
        register(context, BLUEBERRY_BUSH_PATCH_DEFAULT,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLUEBERRY_BUSH_PATCH),
                NoiseThresholdCountPlacement.of(0.1, 3, 0),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, BLUEBERRY_BUSH_PATCH_RARE,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLUEBERRY_BUSH_PATCH),
                RarityFilter.onAverageOnceEvery(20),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, BLUEBERRY_BUSH_PATCH_IRRADIATED,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLUEBERRY_BUSH_PATCH),
                RarityFilter.onAverageOnceEvery(24),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, ORANGE_TREE_PATCH, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORANGE_TREE),
                NoiseBasedCountPlacement.of(3, 10, 0),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome());
        register(context, ORANGE_TREE_PATCH_RARE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORANGE_TREE),
                RarityFilter.onAverageOnceEvery(20),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome());
        register(context, ORANGE_TREE_PATCH_IRRADIATED, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORANGE_TREE),
                RarityFilter.onAverageOnceEvery(24),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome());
        register(context, BRETTL_PATCH_LAKE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRETTL_PLANT),
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BRETTL_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome());
        register(context, BRETTL_PATCH_IRRADIATED, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRETTL_PLANT),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_BRETTL_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid())),
                BiomeFilter.biome());

        register(context, HOLY_ISLES_FLOWER_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.HOLY_ISLES_FLOWER_PATCH),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(16),
                RandomOffsetPlacement.ofTriangle(8, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, HIGHFIELDS_FLOWER_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.HIGHFIELDS_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(0.8, 1, 3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(40),
                RandomOffsetPlacement.ofTriangle(8, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, HIGHFIELDS_FLOWER_FIELD,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.HIGHFIELDS_FLOWER_FIELD),
                NoiseThresholdCountPlacement.of(-0.5, 2, 8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(80),
                RandomOffsetPlacement.ofTriangle(8, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, MAGNETIC_FLOWER_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MAGNETIC_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(0.8, 1, 3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(40),
                RandomOffsetPlacement.ofTriangle(8, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, ARCTIC_FLOWER_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ARCTIC_FLOWER_PATCH),
                NoiseThresholdCountPlacement.of(0.8, 1, 3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(40),
                RandomOffsetPlacement.ofTriangle(8, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_AETHER_PLANT), new MossyPredicate(Vec3i.ZERO.below())), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );

        register(context, MAGNETIC_SHROOM_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MAGNETIC_SHROOM_PATCH),
                CountPlacement.of(UniformInt.of(0, 12)),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome(),
                CountPlacement.of(18),
                RandomOffsetPlacement.ofTriangle(6, 1),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_MAGNETIC_SHROOM), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, BONUS_MAGNETIC_SHROOM_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MAGNETIC_SHROOM_PATCH),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                PlacementUtils.FULL_RANGE,
                BiomeFilter.biome(),
                CountPlacement.of(18),
                RandomOffsetPlacement.ofTriangle(6, 1),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(Vec3i.ZERO.below(), AetherIITags.Blocks.SUPPORTS_MAGNETIC_SHROOM), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, MYCELIAL_MAGNETIC_SHROOM_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MAGNETIC_SHROOM_PATCH),
                CountPlacement.of(4),
                RandomOffsetPlacement.ofTriangle(4, 1),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(Vec3i.ZERO.below(), AetherIIBlocks.MYCELIAL_AETHER_DIRT.get()), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );
        register(context, BRYALINN_FLOWER_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRYALINN_FLOWER_PATCH),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(96),
                RandomOffsetPlacement.ofTriangle(7, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(new MossyPredicate(Vec3i.ZERO.below()), BlockPredicate.replaceable(), BlockPredicate.noFluid()))
        );

        register(context, SHORT_ARILUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SHORT_ARILUM),
                new LakePlacementModifier(),
                RarityFilter.onAverageOnceEvery(4),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_ARILUM), BlockPredicate.matchesBlocks(Blocks.WATER))),
                BiomeFilter.biome());
        register(context, ARILUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ARILUM),
                new LakePlacementModifier(),
                RarityFilter.onAverageOnceEvery(36),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_ARILUM), BlockPredicate.matchesBlocks(Blocks.WATER))),
                BiomeFilter.biome());
        register(context, BLOOMING_ARILUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLOOMING_ARILUM),
                new LakePlacementModifier(),
                RarityFilter.onAverageOnceEvery(12),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_ARILUM), BlockPredicate.matchesBlocks(Blocks.WATER))),
                BiomeFilter.biome());
        register(context, MIXED_ARILUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MIXED_ARILUM),
                new LakePlacementModifier(),
                NoiseBasedCountPlacement.of(30, 40.0, 0.0),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_ARILUM), BlockPredicate.matchesBlocks(Blocks.WATER))),
                BiomeFilter.biome());
        register(context, POND_ARILUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.POND_ARILUM),
                NoiseBasedCountPlacement.of(25, 10.0, 0.5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.SUPPORTS_ARILUM), BlockPredicate.matchesBlocks(Blocks.WATER))),
                BiomeFilter.biome());

        register(context, TREE_MOSS_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREE_MOSS_COVER), BiomeFilter.biome());

        register(context, AETHER_GRASS_BONEMEAL, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.AETHER_GRASS_BONEMEAL), PlacementUtils.isEmpty());
        register(context, ARILUM_BONEMEAL, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ARILUM_BONEMEAL), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)));
    }

    public static void bootstrapTrees(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Highfields
        register(context, FLOURISHING_FIELD_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_FLOURISHING_FIELD),
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                CountPlacement.of(128),
                RandomOffsetPlacement.of(UniformInt.of(-12, 12), UniformInt.of(-6, 6)),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable()),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.north())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.east())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.south())),
                BlockPredicateFilter.forPredicate(BlockPredicate.replaceable(Vec3i.ZERO.west())),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                new StructureBlacklistFilter(AetherIITags.Structures.TREE_BLACKLIST_FILTER));
        register(context, VERDANT_WOODS_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_VERDANT_WOODS),
                HolyIslesPlacementBuilders.treePlacement(PlacementUtils.countExtra(16, 0.1F, 1)));
        register(context, SHROUDED_FOREST_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_SHROUDED_FOREST),
                HolyIslesPlacementBuilders.treePlacement(PlacementUtils.countExtra(150, 0.25F, 50)));
        register(context, SHIMMERING_BASIN_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_SHIMMERING_BASIN),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(5)));
        register(context, SHIMMERING_BASIN_TREES_SUNKEN, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.WISPROOT),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(2),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AetherIIBlocks.WISPROOT_SAPLING.get().defaultBlockState(), BlockPos.ZERO)));

        // Magnetic
        register(context, MAGNETIC_SCAR_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_MAGNETIC_SCAR),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        register(context, TURQUOISE_FOREST_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_TURQUOISE_FOREST),
                HolyIslesPlacementBuilders.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
        register(context, GLISTENING_SWAMP_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_GLISTENING_SWAMP),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(10)));
        register(context, GLISTENING_SWAMP_TREES_SUNKEN, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SWAMP_GREATROOT),
                RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(3),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AetherIIBlocks.GREATROOT_SAPLING.get().defaultBlockState(), BlockPos.ZERO)));
        register(context, GLISTENING_SWAMP_MAGNETIC_SHROOMS, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.MAGNETIC_SHROOMS_BIOME_GLISTENING_SWAMP),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, VIOLET_HIGHWOODS_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_VIOLET_HIGHWOODS),
                HolyIslesPlacementBuilders.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

        // Arctic
        register(context, FRIGID_SIERRA_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_FRIGID_SIERRA),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(4))); //16
        register(context, ENDURING_WOODLAND_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_ENDURING_WOODLANDS),
                HolyIslesPlacementBuilders.treePlacement(PlacementUtils.countExtra(20, 0.1F, 4)));
        register(context, FROZEN_LAKES_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_BIOME_FROZEN_LAKES),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(1)));

        // Irradiated
        register(context, CONTAMINATED_JUNGLE_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_IRRADIATED),
                HolyIslesPlacementBuilders.treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));
        register(context, BATTLEGROUND_WASTES_TREES, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.TREES_IRRADIATED),
                HolyIslesPlacementBuilders.treePlacement(RarityFilter.onAverageOnceEvery(3)));
    }

    public static void bootstrapUnderground(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, GRASS_AND_DIRT_FLOOR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.GRASS_AND_DIRT_FLOOR),
                CountPlacement.of(65),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 220)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, -32, 2),
                BiomeFilter.biome()
        );
        register(context, ENCHANTED_GRASS_AND_DIRT_FLOOR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ENCHANTED_GRASS_AND_DIRT_FLOOR),
                CountPlacement.of(80),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(80), VerticalAnchor.top(), 250)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, -32, 2),
                BiomeFilter.biome()
        );
        register(context, SMALL_MYCELIUM_FLOOR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SMALL_MYCELIUM_FLOOR),
                RarityFilter.onAverageOnceEvery(3),
                CountPlacement.of(60),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(80), VerticalAnchor.top(), 250)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );
        register(context, BIG_MYCELIUM_FLOOR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BIG_MYCELIUM_FLOOR),
                CountPlacement.of(256),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.UP, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 24)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, -32, 0),
                BiomeFilter.biome()
        );
        register(context, COARSE_AETHER_DIRT_FLOOR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COARSE_AETHER_DIRT_FLOOR),
                CountPlacement.of(45),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(96), VerticalAnchor.top(), 240)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );
        register(context, COARSE_AETHER_DIRT_CEILING, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COARSE_AETHER_DIRT_CEILING),
                CountPlacement.of(90),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.solid(), 12)),
                BiomeFilter.biome()
        );
        register(context, COARSE_AETHER_DIRT_FROSTED_CEILING, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COARSE_AETHER_DIRT_FROSTED_CEILING),
                CountPlacement.of(90),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.solid(), 12)),
                BiomeFilter.biome()
        );
        register(context, COARSE_AETHER_DIRT_OVERHANG, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COARSE_AETHER_DIRT_CEILING),
                NoiseBasedCountPlacement.of(50, 10, 0.0),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new ScanPredicate(Direction.DOWN, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 8)),
                BiomeFilter.biome()
        );
        register(context, ICE_OVERHANG, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ICE_CEILING),
                CountPlacement.of(4),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(112), VerticalAnchor.top(), 208)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(new ScanPredicate(Direction.DOWN, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 8)),
                BiomeFilter.biome()
        );
        register(context, POINTED_HOLYSTONE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.POINTED_HOLYSTONE),
                CountPlacement.of(UniformInt.of(64, 104)),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(176))),
                RarityFilter.onAverageOnceEvery(75),
                CountPlacement.of(UniformInt.of(3, 15)),
                RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -4),
                BiomeFilter.biome()
        );
        register(context, POINTED_ICHORITE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.POINTED_ICHORITE),
                CountPlacement.of(UniformInt.of(96, 128)),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(64))),
                RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -4),
                BiomeFilter.biome()
        );
        register(context, BRYALINN_MOSS_CARPET_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRYALINN_MOSS_CARPET),
                CountPlacement.of(3),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, new HasSturdyFacePredicate(BlockPos.ZERO.below(), Direction.UP)))
        );
        register(context, BRYALINN_MOSS_FLOWER_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRYALINN_MOSS_FLOWERS),
                CountPlacement.of(3),
                RandomOffsetPlacement.ofTriangle(2, 2)
        );
        register(context, SHAYELINN_MOSS_CARPET_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SHAYELINN_MOSS_CARPET),
                CountPlacement.of(3),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, new HasSturdyFacePredicate(BlockPos.ZERO.below(), Direction.UP)))
        );
        register(context, AMBRELINN_MOSS_CARPET_PATCH,
                configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.AMBRELINN_MOSS_CARPET),
                CountPlacement.of(3),
                RandomOffsetPlacement.ofTriangle(2, 2),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, new HasSturdyFacePredicate(BlockPos.ZERO.below(), Direction.UP)))
        );
        register(context, EXPOSED_BRYALINN_MOSS_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRYALINN_MOSS_FLOOR),
                NoiseBasedCountPlacement.of(35, 50, 0.0),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                new ElevationFilter(VerticalAnchor.bottom(), VerticalAnchor.belowTop(276)),
                BiomeFilter.biome()
        );
        register(context, SWAMP_BRYALINN_MOSS_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRYALINN_MOSS_FLOOR_SWAMP),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(126), VerticalAnchor.aboveBottom(180))),
                new ElevationFilter(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );
        register(context, EXPOSED_SHAYELINN_MOSS_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.SHAYELINN_MOSS_FLOOR),
                NoiseBasedCountPlacement.of(8, 30, 0.0),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                new ElevationFilter(VerticalAnchor.bottom(), VerticalAnchor.top()),
                BiomeFilter.biome()
        );

        register(context, UNSTABLE_HOLYSTONE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.UNSTABLE_HOLYSTONE),
                RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(96), VerticalAnchor.top())),
                BiomeFilter.biome()
        );
        register(context, UNSTABLE_UNDERSHALE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.UNSTABLE_UNDERSHALE),
                RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96))),
                BiomeFilter.biome()
        );

        register(context, ALKAHEST_POOL_RARE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ALKAHEST_POOL),
                CountPlacement.of(8),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-96), VerticalAnchor.aboveBottom(64))),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -4),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), BlockPredicate.ONLY_IN_AIR_PREDICATE, 16),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.UP, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 8)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().north(6), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().east(6), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().south(6), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().west(6), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                new StructureBlacklistFilter(AetherIITags.Structures.ALKAHEST_POOL_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, ALKAHEST_POOL, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ALKAHEST_POOL),
                CountPlacement.of(96),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(64))),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -4),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), BlockPredicate.ONLY_IN_AIR_PREDICATE, 8),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.UP, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 4)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().north(3), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().east(3), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().south(3), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(BlockPos.ZERO.below().west(3), AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 2)),
                new StructureBlacklistFilter(AetherIITags.Structures.ALKAHEST_POOL_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );

        register(context, ORE_SCATTERGLASS, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_SCATTERGLASS),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
        register(context, ORE_ICESTONE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_ICESTONE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(9, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
        register(context, ORE_ICESTONE_SMALL, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_ICESTONE_SMALL),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top())));
        register(context, ORE_AGIOSITE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_AGIOSITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-96), VerticalAnchor.aboveBottom(96))));
        register(context, ORE_AGIOSITE_SMALL, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_AGIOSITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-96), VerticalAnchor.aboveBottom(96), 72))));

        register(context, ORE_HOLYSTONE_QUARTZ, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_HOLYSTONE_QUARTZ),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(128), VerticalAnchor.top())));
        register(context, ORE_AMBROSIUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_AMBROSIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(20, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(24), VerticalAnchor.top(), 96))));
        register(context, ORE_ZANITE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_ZANITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(55), VerticalAnchor.aboveBottom(160))));
        register(context, ORE_ZANITE_MOUNTAIN, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_ZANITE_MOUNTAIN),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(35), VerticalAnchor.top())));
        register(context, ORE_GLINT, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_GLINT),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(114))));
        register(context, ORE_ARKENIUM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_ARKENIUM),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(11, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(90), 32))));
        register(context, ORE_GRAVITITE_BURIED, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_GRAVITITE_BURIED),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(4, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(80), 20))));
        register(context, ORE_GRAVITITE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_GRAVITITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-50), VerticalAnchor.aboveBottom(70), 40))));
        register(context, ORE_CORROBONITE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_CORROBONITE),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(3, HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-65), VerticalAnchor.aboveBottom(65), 35))));

        register(context, ORE_HESTVEIL_OPEN, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_HESTVEIL_OPEN),
                CountPlacement.of(16),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(96))),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.UP, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 4)),
                BlockPredicateFilter.forPredicate(new SearchPredicate(Direction.DOWN, BlockPredicate.matchesTag(AetherIITags.Blocks.AETHER_UNDERGROUND_BLOCKS), 4)),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -4),
                new StructureBlacklistFilter(AetherIITags.Structures.ALKAHEST_POOL_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, ORE_HESTVEIL_BURIED, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ORE_HESTVEIL_BURIED),
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.aboveBottom(-96), VerticalAnchor.aboveBottom(128))),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -4),
                new StructureBlacklistFilter(AetherIITags.Structures.ALKAHEST_POOL_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
    }

    public static void bootstrapWorldgen(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, COARSE_AETHER_DIRT_SURFACE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COARSE_AETHER_DIRT_SURFACE),
                CountPlacement.of(5),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );
        register(context, DISK_BRYALINN_MOSS, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.DISK_BRYALINN_MOSS),
                CountPlacement.of(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
                BiomeFilter.biome()
        );
        register(context, LAKE_DISK_BRYALINN_MOSS, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.DISK_BRYALINN_MOSS),
                new LakePlacementModifier(),
                RarityFilter.onAverageOnceEvery(25),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
                BiomeFilter.biome()
        );

        register(context, COAST_QUICKSOIL, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COAST_QUICKSOIL),
                CountPlacement.of(6),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                new StructureBlacklistFilter(AetherIITags.Structures.COAST_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, COAST_QUICKSOIL_SPARSE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COAST_QUICKSOIL),
                CountPlacement.of(2),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                new StructureBlacklistFilter(AetherIITags.Structures.COAST_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, COAST_FERROSITE_SAND, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COAST_FERROSITE_SAND),
                CountPlacement.of(4),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                new StructureBlacklistFilter(AetherIITags.Structures.COAST_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, COAST_FERROSITE_PILLAR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COAST_FERROSITE_PILLAR),
                CountPlacement.of(18),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(144), VerticalAnchor.absolute(256)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 16), //TODO find out why it isn't working
                BiomeFilter.biome()
        );
        register(context, COAST_ARCTIC_PACKED_ICE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COAST_ARCTIC_PACKED_ICE),
                CountPlacement.of(3),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(144)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                new StructureBlacklistFilter(AetherIITags.Structures.COAST_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );

        register(context, WATER_POND, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.WATER_POND),
                RarityFilter.onAverageOnceEvery(25),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.AERCLOUDS))),
                BiomeFilter.biome());
        register(context, WATER_POND_UNDERGROUND, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.WATER_POND),
                RarityFilter.onAverageOnceEvery(15),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 16),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5),
                BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.AERCLOUDS))),
                BiomeFilter.biome());
        register(context, WATER_POND_TUNDRA, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.WATER_POND_TUNDRA),
                RarityFilter.onAverageOnceEvery(2),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.AERCLOUDS))),
                BiomeFilter.biome());
        register(context, WATER_SPRING, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.WATER_SPRING),
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.aboveBottom(256)),
                BiomeFilter.biome());
        register(context, BONUS_WATER_SPRING, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.WATER_SPRING),
                CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(128), VerticalAnchor.aboveBottom(200)),
                BiomeFilter.biome());
        register(context, NOISE_LAKE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.NOISE_LAKE), BiomeFilter.biome());
        register(context, NOISE_LAKE_ARCTIC, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.NOISE_LAKE_ARCTIC), BiomeFilter.biome());
        register(context, NOISE_LAKE_SWAMP, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.NOISE_LAKE_SWAMP), BiomeFilter.biome());

        register(context, FERROSITE_PILLAR, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.FERROSITE_PILLAR),
                CountPlacement.of(1),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.absolute(200)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.FERROSITE_PILLAR_GENERATES_ON)),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        register(context, FERROSITE_SPIKE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.FERROSITE_SPIKE),
                CountPlacement.of(12),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(112), VerticalAnchor.absolute(256)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.FERROSITE_SPIKE_GENERATES_ON)),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                new StructureBlacklistFilter(AetherIITags.Structures.FERROSITE_SPIKE_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, COASTAL_ARCTIC_ICE_SPIKE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ARCTIC_ICE_SPIKE),
                CountPlacement.of(2),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(112), VerticalAnchor.absolute(136)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON)),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                new StructureBlacklistFilter(AetherIITags.Structures.ARCTIC_ICE_SPIKE_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );
        register(context, ARCTIC_ICE_SPIKE_CLUSTER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ARCTIC_ICE_SPIKE_VARIANTS),
                NoiseBasedCountPlacement.of(10, 200.0, 0.0),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.absolute(224)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.ARCTIC_ICE_SPIKE_GENERATES_ON)),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                new StructureBlacklistFilter(AetherIITags.Structures.ARCTIC_ICE_SPIKE_BLACKLIST_FILTER),
                BiomeFilter.biome()
        );

        register(context, FREEZE_TOP_LAYER_ARCTIC, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.FREEZE_TOP_LAYER_ARCTIC), BiomeFilter.biome());
        register(context, FREEZE_TOP_LAYER_TUNDRA, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.FREEZE_TOP_LAYER_TUNDRA), BiomeFilter.biome());

        register(context, CRATER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.CRATER),
                RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(96), VerticalAnchor.top()),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), AetherIITags.Blocks.AETHER_GROUND_BLOCKS)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below().north(3), AetherIITags.Blocks.AETHER_GROUND_BLOCKS)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below().east(3), AetherIITags.Blocks.AETHER_GROUND_BLOCKS)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below().south(3), AetherIITags.Blocks.AETHER_GROUND_BLOCKS)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below().west(3), AetherIITags.Blocks.AETHER_GROUND_BLOCKS)),
                RandomOffsetPlacement.vertical(UniformInt.of(-2, 0)),
                BiomeFilter.biome()
        );

        register(context, CLOUDBED, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.CLOUDBED), BiomeFilter.biome());

    }

    public static void bootstrapAir(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, HIGH_STORM_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.STORM_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(288), VerticalAnchor.aboveBottom(320)),
                RarityFilter.onAverageOnceEvery(1024),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, HIGH_GREEN_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.GREEN_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(256), VerticalAnchor.aboveBottom(304)),
                RarityFilter.onAverageOnceEvery(512),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, HIGH_PURPLE_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.PURPLE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(256), VerticalAnchor.aboveBottom(304)),
                RarityFilter.onAverageOnceEvery(512),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());

        register(context, MIDDLE_COLD_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COLD_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(208), VerticalAnchor.aboveBottom(256)),
                RarityFilter.onAverageOnceEvery(48),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, MIDDLE_STORM_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.STORM_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(208), VerticalAnchor.aboveBottom(256)),
                RarityFilter.onAverageOnceEvery(768),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());

        register(context, SURFACE_COLD_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COLD_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(36),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, SURFACE_GOLDEN_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.GOLDEN_AERCLOUD),
                HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(384),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, SURFACE_BLUE_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLUE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(128),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, SURFACE_GREEN_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.GREEN_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(196),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, SURFACE_PURPLE_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.PURPLE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(96), VerticalAnchor.aboveBottom(208)),
                RarityFilter.onAverageOnceEvery(156),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());

        register(context, LOWER_STORM_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.STORM_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(64), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(64),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, LOWER_BLUE_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BLUE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(128),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, LOWER_GREEN_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.GREEN_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(128),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
        register(context, LOWER_PURPLE_AERCLOUD, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.PURPLE_AERCLOUD),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(96)),
                RarityFilter.onAverageOnceEvery(128),
                new StructureBlacklistFilter(AetherIITags.Structures.AERCLOUD_BLACKLIST_FILTER),
                BiomeFilter.biome());
    }

    public static void bootstrapDungeon(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, BRYALINN_MOSS_COVER_STRUCTURE, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.BRYALINN_MOSS_STRUCTURE));

        register(context, LARGE_SHELF_ROTSHROOM, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.LARGE_SHELF_ROTSHROOM));
        register(context, LARGE_SHELF_ROTSHROOM_UNDERGROUND, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.LARGE_SHELF_ROTSHROOM_UNDERGROUND));
        register(context, ROTSHROOM_PATCH, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.ROTSHROOM_PATCH),
                CountPlacement.of(32),
                RandomOffsetPlacement.ofTriangle(4, 3),
                BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
        );
        register(context, COARSE_AETHER_DIRT_DUNGEON, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.COARSE_AETHER_DIRT_DUNGEON));
        register(context, UNDERGROWTH_PATCH, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.UNDERGROWTH_PATCH));

        register(context, INFECTED_GUARDIAN_TREE_ENTRANCE_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.INFECTED_GUARDIAN_TREE_ENTRANCE_COVER));
        register(context, INFECTED_GUARDIAN_TREE_STAIRCASE_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.INFECTED_GUARDIAN_TREE_STAIRCASE_COVER));
        register(context, INFECTED_GUARDIAN_TREE_LOBBY_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.INFECTED_GUARDIAN_TREE_LOBBY_COVER));
        register(context, INFECTED_GUARDIAN_TREE_BOSS_ROOM_COVER, configuredFeatures.getOrThrow(HolyIslesConfiguredFeatures.INFECTED_GUARDIAN_TREE_BOSS_ROOM_COVER));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(AetherII.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}