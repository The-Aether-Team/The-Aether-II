package com.aetherteam.aetherii.data.resources.registries.placement;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.data.resources.builders.AetherIIPlacementBuilders;
import com.aetherteam.aetherii.data.resources.registries.features.AetherIIMiscFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class AetherIIMiscPlacements {
    public static final ResourceKey<PlacedFeature> COAST_QUICKSOIL = AetherIIPlacementUtils.createKey("coast_quicksoil");
    public static final ResourceKey<PlacedFeature> COAST_FERROSITE_SAND = AetherIIPlacementUtils.createKey("coast_ferrosite_sand");
    public static final ResourceKey<PlacedFeature> COAST_ARCTIC_PACKED_ICE = AetherIIPlacementUtils.createKey("coast_arctic_packed_ice");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE = AetherIIPlacementUtils.createKey("noise_lake");
    public static final ResourceKey<PlacedFeature> NOISE_LAKE_ARCTIC = AetherIIPlacementUtils.createKey("noise_lake_arctic");
    public static final ResourceKey<PlacedFeature> FERROSITE_PILLAR = AetherIIPlacementUtils.createKey("ferrosite_pillar");
    public static final ResourceKey<PlacedFeature> COASTAL_ARCTIC_ICE_SPIKE = AetherIIPlacementUtils.createKey("coastal_arctic_ice_spike");
    public static final ResourceKey<PlacedFeature> ARCTIC_ICE_SPIKE_CLUSTER = AetherIIPlacementUtils.createKey("arctic_ice_spike_cluster");
    public static final ResourceKey<PlacedFeature> AETHER_WATER_LAKE = AetherIIPlacementUtils.createKey("aether_water_lake");
    public static final ResourceKey<PlacedFeature> AETHER_WATER_LAKE_UNDERGROUND = AetherIIPlacementUtils.createKey("aether_water_lake_underground");
    public static final ResourceKey<PlacedFeature> AETHER_WATER_SPRING = AetherIIPlacementUtils.createKey("aether_water_spring");
    public static final ResourceKey<PlacedFeature> SKYROOT_TWIGS = AetherIIPlacementUtils.createKey("skyroot_twigs");
    public static final ResourceKey<PlacedFeature> HOLYSTONE_ROCKS = AetherIIPlacementUtils.createKey("holystone_rocks");
    public static final ResourceKey<PlacedFeature> MOA_NEST = AetherIIPlacementUtils.createKey("moa_nest");
    public static final ResourceKey<PlacedFeature> COLD_AERCLOUD = AetherIIPlacementUtils.createKey("cold_aercloud");
    public static final ResourceKey<PlacedFeature> BLUE_AERCLOUD = AetherIIPlacementUtils.createKey("blue_aercloud");
    public static final ResourceKey<PlacedFeature> GOLDEN_AERCLOUD = AetherIIPlacementUtils.createKey("golden_aercloud");
    public static final ResourceKey<PlacedFeature> GREEN_AERCLOUD = AetherIIPlacementUtils.createKey("green_aercloud");
    public static final ResourceKey<PlacedFeature> PURPLE_AERCLOUD = AetherIIPlacementUtils.createKey("purple_aercloud");
    public static final ResourceKey<PlacedFeature> STORM_AERCLOUD = AetherIIPlacementUtils.createKey("storm_aercloud");
    public static final ResourceKey<PlacedFeature> CLOUDBED = AetherIIPlacementUtils.createKey("cloudbed");
    public static final ResourceKey<PlacedFeature> FREEZE_TOP_LAYER_ARCTIC = AetherIIPlacementUtils.createKey("freeze_top_layer_arctic");

    @SuppressWarnings("deprecation")
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        AetherIIPlacementUtils.register(context, COAST_QUICKSOIL, configuredFeatures.getOrThrow(AetherIIMiscFeatures.COAST_QUICKSOIL),
                CountPlacement.of(6),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );
        AetherIIPlacementUtils.register(context, COAST_FERROSITE_SAND, configuredFeatures.getOrThrow(AetherIIMiscFeatures.COAST_FERROSITE_SAND),
                CountPlacement.of(6),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(156)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );
        AetherIIPlacementUtils.register(context, COAST_ARCTIC_PACKED_ICE, configuredFeatures.getOrThrow(AetherIIMiscFeatures.COAST_ARCTIC_PACKED_ICE),
                CountPlacement.of(3),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(112), VerticalAnchor.absolute(144)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 5),
                BiomeFilter.biome()
        );

        AetherIIPlacementUtils.register(context, NOISE_LAKE, configuredFeatures.getOrThrow(AetherIIMiscFeatures.NOISE_LAKE), BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, NOISE_LAKE_ARCTIC, configuredFeatures.getOrThrow(AetherIIMiscFeatures.NOISE_LAKE_ARCTIC), BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, FERROSITE_PILLAR, configuredFeatures.getOrThrow(AetherIIMiscFeatures.FERROSITE_PILLAR),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.absolute(200)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.AETHER_DIRT)), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        AetherIIPlacementUtils.register(context, COASTAL_ARCTIC_ICE_SPIKE, configuredFeatures.getOrThrow(AetherIIMiscFeatures.ARCTIC_ICE_SPIKE),
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(112), VerticalAnchor.absolute(144)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get())), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );
        AetherIIPlacementUtils.register(context, ARCTIC_ICE_SPIKE_CLUSTER, configuredFeatures.getOrThrow(AetherIIMiscFeatures.ARCTIC_ICE_SPIKE),
                NoiseBasedCountPlacement.of(12, 200.0, 0.0),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(128), VerticalAnchor.absolute(224)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), AetherIIBlocks.AETHER_GRASS_BLOCK.get(), AetherIIBlocks.ARCTIC_SNOW_BLOCK.get())), //todo
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        AetherIIPlacementUtils.register(context, AETHER_WATER_LAKE, configuredFeatures.getOrThrow(AetherIIMiscFeatures.AETHER_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(25),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, AETHER_WATER_LAKE_UNDERGROUND, configuredFeatures.getOrThrow(AetherIIMiscFeatures.AETHER_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(15),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 16),
                SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5),
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, AETHER_WATER_SPRING, configuredFeatures.getOrThrow(AetherIIMiscFeatures.AETHER_WATER_SPRING),
                CountPlacement.of(15),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.aboveBottom(256)),
                BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, SKYROOT_TWIGS, configuredFeatures.getOrThrow(AetherIIMiscFeatures.SKYROOT_TWIGS),
                NoiseThresholdCountPlacement.of(-0.8, 1, 2),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.AETHER_DIRT)), //todo
                BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, HOLYSTONE_ROCKS, configuredFeatures.getOrThrow(AetherIIMiscFeatures.HOLYSTONE_ROCKS),
                NoiseThresholdCountPlacement.of(-0.8, 1, 2),
                CountOnEveryLayerPlacement.of(UniformInt.of(0, 1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new BlockPos(0, -1, 0), AetherIITags.Blocks.AETHER_DIRT)), //todo
                BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, MOA_NEST, configuredFeatures.getOrThrow(AetherIIMiscFeatures.MOA_NEST),
                RarityFilter.onAverageOnceEvery(6),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(128), VerticalAnchor.absolute(200)),
                PlacementUtils.filteredByBlockSurvival(AetherIIBlocks.SKYROOT_SAPLING.get()),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        AetherIIPlacementUtils.register(context, CLOUDBED, configuredFeatures.getOrThrow(AetherIIMiscFeatures.CLOUDBED), BiomeFilter.biome());
        AetherIIPlacementUtils.register(context, FREEZE_TOP_LAYER_ARCTIC, configuredFeatures.getOrThrow(AetherIIMiscFeatures.FREEZE_TOP_LAYER_ARCTIC), BiomeFilter.biome());

        AetherIIPlacementUtils.register(context, COLD_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.COLD_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 12));
        AetherIIPlacementUtils.register(context, BLUE_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.BLUE_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 28));
        AetherIIPlacementUtils.register(context, GOLDEN_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.GOLDEN_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(180, 272, 32));
        AetherIIPlacementUtils.register(context, GREEN_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.GREEN_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 28));
        AetherIIPlacementUtils.register(context, PURPLE_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.PURPLE_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(32, 256, 28));
        AetherIIPlacementUtils.register(context, STORM_AERCLOUD, configuredFeatures.getOrThrow(AetherIIMiscFeatures.STORM_AERCLOUD), AetherIIPlacementBuilders.aercloudPlacement(16, 96, 30));
    }
}